package com.example.playlistmaker.audio_player.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerInteractor
import com.example.playlistmaker.audio_player.data.dto.TrackDto
import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.audio_player.domain.models.AudioPlayerState
import com.example.playlistmaker.audio_player.domain.models.AudioPlayerViewState
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerViewModel(
    private val audioPlayerInteractorImpl: AudioPlayerInteractor
) : ViewModel() {

    private val _audioPlayerViewState = MutableLiveData<AudioPlayerViewState>()
    val audioPlayerViewState: LiveData<AudioPlayerViewState> get() = _audioPlayerViewState

    private val _trackDtoApp = MutableLiveData<TrackDtoApp>()
    val trackDtoApp: LiveData<TrackDtoApp> get() = _trackDtoApp
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            refreshTimeNowPlay()
            handler.postDelayed(this, REFRESH_LIST_DELAY_MILLIS)
        }
    }

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
        handler.postDelayed(runnable, REFRESH_LIST_DELAY_MILLIS)
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
        handler.removeCallbacks(runnable)
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
        private const val REFRESH_LIST_DELAY_MILLIS = 500L
    }
}