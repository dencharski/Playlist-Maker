package com.example.playlistmaker.audio_player.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerInteractor
import com.example.playlistmaker.audio_player.data.dto.TrackDto
import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.audio_player.domain.models.AudioPlayerState
import com.example.playlistmaker.audio_player.domain.models.AudioPlayerViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerViewModel(
    private val audioPlayerInteractorImpl: AudioPlayerInteractor
) : ViewModel() {

    private val _audioPlayerViewState = MutableLiveData<AudioPlayerViewState>()
    val audioPlayerViewState: LiveData<AudioPlayerViewState> get() = _audioPlayerViewState

    private val _trackDtoApp = MutableLiveData<TrackDtoApp>()
    val trackDtoApp: LiveData<TrackDtoApp> get() = _trackDtoApp

    private var refreshTimeJob: Job? = null
    fun setDataExtrasTrack(track: TrackDto?) {
        if (track != null) {
            _trackDtoApp.postValue(
                TrackDtoApp(
                    track.trackId,
                    track.trackName,
                    track.artistName,
                    track.getTrackTime().toString(),
                    track.artworkUrl100,
                    track.collectionName,
                    track.releaseDate,
                    track.primaryGenreName,
                    track.country,
                    track.previewUrl
                )
            )

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