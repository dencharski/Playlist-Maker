package com.example.playlistmaker.audio_player.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerInteractor
import com.example.playlistmaker.main.domain.models.TrackApp
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerSelectedTrackInteractor
import com.example.playlistmaker.audio_player.domain.models.AudioPlayerState
import com.example.playlistmaker.audio_player.domain.models.AudioPlayerViewState
import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerPlayListInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerViewModel(
    private val audioPlayerInteractor: AudioPlayerInteractor,
    private val audioPlayerSelectedTrackInteractor: AudioPlayerSelectedTrackInteractor,
    private val audioPlayerPlayListInteractor: AudioPlayerPlayListInteractor
) : ViewModel() {

    private val _audioPlayerViewState = MutableLiveData<AudioPlayerViewState>()
    val audioPlayerViewState: LiveData<AudioPlayerViewState> get() = _audioPlayerViewState

    private val _trackApp = MutableLiveData<TrackApp?>()
    val trackApp: MutableLiveData<TrackApp?> get() = _trackApp

    private var refreshTimeJob: Job? = null

    private var _currentTrackId: Long = -1

    fun onFavoriteClicked() {
        Log.d("track", "AudioPlayeriewModel.onFavoriteClicked")
        viewModelScope.launch {
            if (_trackApp.value?.isFavorite == false) {

                audioPlayerSelectedTrackInteractor.insertOneTrack(_trackApp.value!!)
                _audioPlayerViewState.postValue(AudioPlayerViewState.AddFavoriteClick(isFavorite = true))
                _trackApp.value?.isFavorite = true

            } else {

                _trackApp.value?.let { audioPlayerSelectedTrackInteractor.deleteOneTrack(it) }
                _audioPlayerViewState.postValue(AudioPlayerViewState.AddFavoriteClick(isFavorite = false))
                _trackApp.value?.isFavorite = false

            }
        }
    }

    fun getListOfPlayLists() {
        viewModelScope.launch {
            audioPlayerPlayListInteractor.getListOfPlayLists().collect {
                if (it.isEmpty()) {
                    _audioPlayerViewState.postValue(AudioPlayerViewState.ListOfPlayListsIsEmpty)
                } else {
                    _audioPlayerViewState.postValue(AudioPlayerViewState.ListOfPlayLists(list = it))
                }

            }
        }
    }

    fun checkTrackInPlaylist(playList: PlayList) {

        if (audioPlayerPlayListInteractor.checkTrackIdInPlayList(playList,_currentTrackId)) {
            _audioPlayerViewState.postValue(AudioPlayerViewState.PlayListContainTrack)
        } else {
            insertChangesInPlayListAndTrack(playList)
            _audioPlayerViewState.postValue(AudioPlayerViewState.AddTrackInPlayList(playList.playListName))
        }
    }

    private fun insertChangesInPlayListAndTrack(playList: PlayList) {
        if (_trackApp.value!=null){
            addTrackInPlayListTable(_trackApp.value!!)
        }

        addTrackIdInNewPlayList(playList)
    }

    private fun addTrackIdInNewPlayList(playList: PlayList) {
        viewModelScope.launch {
            audioPlayerPlayListInteractor.insertTrackIdInPlayList(playList, _currentTrackId)
        }
    }

    private fun addTrackInPlayListTable(track: TrackApp) {
        viewModelScope.launch {
            audioPlayerPlayListInteractor.addTrackInPlayListTable(track)
        }
    }

    fun setDataExtrasTrack(track: TrackApp?) {
        if (track != null) {
            _currentTrackId = track.trackId
            _trackApp.postValue(track)
            audioPlayerInteractor.setTrack(track.previewUrl)
        } else {
            setAudioPlayerViewStateError()
        }

    }

    fun playbackControl() {
        when (audioPlayerInteractor.playbackControl()) {
            AudioPlayerState.STATE_PREPARED, AudioPlayerState.STATE_PAUSED -> {
                pausePlayer()
            }

            AudioPlayerState.STATE_PLAYING -> {
                startPlayer()
            }
        }
    }

    private fun startPlayer() {

        refreshTimeJob = viewModelScope.launch {
            while (audioPlayerInteractor.getIsPlayingCompleted() == AudioPlayerState.STATE_PLAYING) {
                delay(REFRESH_LIST_DELAY_MILLIS)
                refreshTimeNowPlay()
            }

        }
        _audioPlayerViewState.postValue(AudioPlayerViewState.Play)
    }

    private fun pausePlayer() {
        stopTimer()
        _audioPlayerViewState.postValue(AudioPlayerViewState.Pause)
    }

    private fun refreshTimeNowPlay() {
        if (audioPlayerInteractor.getIsPlayingCompleted() == AudioPlayerState.STATE_DEFAULT_COMPLETED) {
            _audioPlayerViewState.postValue(AudioPlayerViewState.PlayCompleted)
        } else {
            _audioPlayerViewState.value =
                AudioPlayerViewState.CurrentPosition(
                    currentPosition
                    = SimpleDateFormat(
                        "mm:ss",
                        Locale.getDefault()
                    ).format(audioPlayerInteractor.getMediaPlayerCurrentPosition())
                )
        }
    }

    private fun stopTimer() {
        refreshTimeJob?.cancel()
    }

    fun onPause() {
        audioPlayerInteractor.onPause()
        pausePlayer()
    }

    fun onDestroy() {
        stopTimer()
    }

    private fun setAudioPlayerViewStateError() {
        _audioPlayerViewState.postValue(AudioPlayerViewState.Error)
    }

    companion object {
        private const val REFRESH_LIST_DELAY_MILLIS = 300L
    }
}