package com.example.playlistmaker.audio_player_activity.ui

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.App
import com.example.playlistmaker.audio_player_activity.data.dto.AudioPlayerViewState
import com.example.playlistmaker.audio_player_activity.domain.api.AudioPlayerInteractor
import com.example.playlistmaker.audio_player_activity.AudioPlayerCreator
import com.example.playlistmaker.audio_player_activity.domain.models.TrackDto
import com.example.playlistmaker.TrackDtoApp

class AudioPlayerViewModel(
    private val audioPlayerInteractorImpl: AudioPlayerInteractor
) : ViewModel() {

    private val _audioPlayerViewState = MutableLiveData<AudioPlayerViewState>()
    val audioPlayerViewState: LiveData<AudioPlayerViewState> get() = _audioPlayerViewState


    companion object {

        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val audioPlayerInteractorImpl = AudioPlayerCreator.getAudioPlayerInteractor()
                AudioPlayerViewModel(audioPlayerInteractorImpl)
            }
        }
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
        }

    }

    private fun setAudioPlayerViewState(trackDtoApp: TrackDtoApp) {
        _audioPlayerViewState.value = AudioPlayerViewState(
            track = trackDtoApp,
            mediaPlayer = audioPlayerInteractorImpl.getPlayer()
        )
    }

}