package com.example.playlistmaker.audio_player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerInteractor
import com.example.playlistmaker.main.domain.models.TrackDtoApp
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerFavoriteTrackInteractor
import com.example.playlistmaker.audio_player.domain.models.AudioPlayerState
import com.example.playlistmaker.audio_player.domain.models.AudioPlayerViewState
import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.mediateka.domain.api.PlayListInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerViewModel(
    private val audioPlayerInteractorImpl: AudioPlayerInteractor,
    private val audioPlayerFavoriteTrackInteractor: AudioPlayerFavoriteTrackInteractor,
    private val playListInteractor: PlayListInteractor
) : ViewModel() {

    private val _audioPlayerViewState = MutableLiveData<AudioPlayerViewState>()
    val audioPlayerViewState: LiveData<AudioPlayerViewState> get() = _audioPlayerViewState

    private val _trackDtoApp = MutableLiveData<TrackDtoApp>()
    val trackDtoApp: LiveData<TrackDtoApp> get() = _trackDtoApp

    private var refreshTimeJob: Job? = null

    private var _currentTrackId: Long = -1

    fun onFavoriteClicked() {
        viewModelScope.launch {
            if (_trackDtoApp.value?.isFavorite == false) {

                audioPlayerFavoriteTrackInteractor.insertOneTrack(_trackDtoApp.value!!)
                _audioPlayerViewState.postValue(AudioPlayerViewState.AddFavoriteClick(isFavorite = true))
                _trackDtoApp.value?.isFavorite = true

            } else {

                _trackDtoApp.value?.let { audioPlayerFavoriteTrackInteractor.deleteOneTrack(it) }
                _audioPlayerViewState.postValue(AudioPlayerViewState.AddFavoriteClick(isFavorite = false))
                _trackDtoApp.value?.isFavorite = false

            }
        }
    }

    fun getListOfPlayLists() {
        viewModelScope.launch {
            playListInteractor.getListOfPlayLists().collect {
                if (it.isEmpty()) {
                    _audioPlayerViewState.postValue(AudioPlayerViewState.ListOfPlayListsIsEmpty)
                } else {
                    _audioPlayerViewState.postValue(AudioPlayerViewState.ListOfPlayLists(list = it))
                }

            }
        }
    }

    fun checkTrackInPlaylist(playList: PlayList) {
        if (playList.listOfTrackIds.contains(_currentTrackId)) {
            _audioPlayerViewState.postValue(AudioPlayerViewState.PlayListContainTrack)
        } else {
            insertChangesInPlayListAndTrack(playList)
            _audioPlayerViewState.postValue(AudioPlayerViewState.AddTrackInPlayList(playList.playListName))
        }
    }

    private fun insertChangesInPlayListAndTrack(playList: PlayList) {
        _trackDtoApp.value?.let { addTrackInPlayListTable(it) }

        addPlayListWithNewListOfTrackIds(playList)
    }

    private fun addPlayListWithNewListOfTrackIds(playList: PlayList) {
        viewModelScope.launch {
            playListInteractor.insertTrackIdInPlaylist(playList, _currentTrackId)
        }
    }

    private fun addTrackInPlayListTable(track: TrackDtoApp) {
        viewModelScope.launch {
            playListInteractor.insertTrackDtoAppInPlayList(track)
        }
    }

    fun setDataExtrasTrack(track: TrackDtoApp?) {
        if (track != null) {
            _currentTrackId = track.trackId
            _trackDtoApp.postValue(track!!)
            audioPlayerInteractorImpl.setTrack(track.previewUrl)
        } else {
            setAudioPlayerViewStateError()
        }

    }

    fun playbackControl() {
        when (audioPlayerInteractorImpl.playbackControl()) {
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
            while (audioPlayerInteractorImpl.getIsPlayingCompleted() == AudioPlayerState.STATE_PLAYING) {
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
        if (audioPlayerInteractorImpl.getIsPlayingCompleted() == AudioPlayerState.STATE_DEFAULT_COMPLETED) {
            _audioPlayerViewState.postValue(AudioPlayerViewState.PlayCompleted)
        } else {
            _audioPlayerViewState.value =
                AudioPlayerViewState.CurrentPosition(
                    currentPosition
                    = SimpleDateFormat(
                        "mm:ss",
                        Locale.getDefault()
                    ).format(audioPlayerInteractorImpl.getMediaPlayerCurrentPosition())
                )
        }
    }

    private fun stopTimer() {
        refreshTimeJob?.cancel()
    }

    fun onPause() {
        audioPlayerInteractorImpl.onPause()
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