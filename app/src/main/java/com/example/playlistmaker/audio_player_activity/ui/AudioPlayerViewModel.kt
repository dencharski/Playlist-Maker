package com.example.playlistmaker.audio_player_activity.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.App
import com.example.playlistmaker.audio_player_activity.domain.api.AudioPlayerInteractor
import com.example.playlistmaker.audio_player_activity.domain.models.TrackDto
import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.audio_player_activity.data.AudioPlayerRepositoryImpl
import com.example.playlistmaker.audio_player_activity.data.dto.AudioPlayerViewState
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerViewModel(
    private val audioPlayerInteractorImpl: AudioPlayerInteractor
) : ViewModel() {


    private val _audioPlayerViewState = MutableLiveData<AudioPlayerViewState>()
    val audioPlayerViewState: LiveData<AudioPlayerViewState> get() = _audioPlayerViewState


    private val handler = Handler(Looper.getMainLooper())

    private val runnable = object : Runnable {
        override fun run() {
            refreshTimeNowPlay()
            handler.postDelayed(this, REFRESH_LIST_DELAY_MILLIS)
        }
    }

    companion object {
        private const val REFRESH_LIST_DELAY_MILLIS = 500L
    }



    fun setDataExtrasTrack(extras: Bundle?) {
        val track = extras?.getParcelable<TrackDto>(App.trackKey)
        if (track != null) {

            setAudioPlayerViewState(
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
            AudioPlayerRepositoryImpl.STATE_PREPARED,AudioPlayerRepositoryImpl.STATE_PAUSED -> {
                pausePlayer()
            }
            AudioPlayerRepositoryImpl.STATE_PLAYING -> {
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

        _audioPlayerViewState.postValue(
            AudioPlayerViewState.CurrentPosition(
                currentPosition
                = SimpleDateFormat(
                    "mm:ss",
                    Locale.getDefault()
                ).format(audioPlayerInteractorImpl.getMediaPlayerCurrentPosition())
            )
        )

    }

    private fun stopTimer() {
        handler.removeCallbacks(runnable)
    }

    fun onPause() {
        audioPlayerInteractorImpl.onPause()
    }

    fun onDestroy() {
        stopTimer()
    }

    private fun setAudioPlayerViewState(trackDtoApp: TrackDtoApp) {
        _audioPlayerViewState.postValue(AudioPlayerViewState.Track(track = trackDtoApp))
    }

    private fun setAudioPlayerViewStateError() {
        _audioPlayerViewState.postValue(AudioPlayerViewState.Error)
    }

}