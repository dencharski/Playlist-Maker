package com.example.playlistmaker.AudioPlayerActivity.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.App
import com.example.playlistmaker.AudioPlayerActivity.data.dto.AudioPlayerViewState
import com.example.playlistmaker.AudioPlayerActivity.domain.api.AudioPlayerInteractorInterface
import com.example.playlistmaker.AudioPlayerActivity.AudioPlayerCreator
import com.example.playlistmaker.AudioPlayerActivity.domain.models.TrackDto
import com.example.playlistmaker.TrackDtoApp

class AudioPlayerViewModel(
    private val extras: Bundle?,
    private val audioPlayerInteractorImpl: AudioPlayerInteractorInterface
) : ViewModel() {

    private val teg = "AudioPlayerViewModel"

    private val _audioPlayerViewState = MutableLiveData<AudioPlayerViewState>()
    val audioPlayerViewState: LiveData<AudioPlayerViewState> get() = _audioPlayerViewState

    init {
        Log.d(teg, "init")

        _audioPlayerViewState.value = AudioPlayerViewState(
            track = getDataExtrasTrack(extras),
            mediaPlayer = audioPlayerInteractorImpl.getPlayer()
        )
    }

    companion object {

        fun getViewModelFactory(extras: Bundle?): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val audioPlayerInteractorImpl = AudioPlayerCreator.getAudioPlayerInteractor()
                AudioPlayerViewModel(extras, audioPlayerInteractorImpl)
            }
        }
    }

    private fun getDataExtrasTrack(extras: Bundle?): TrackDtoApp? {
        var trackDtoApp : TrackDtoApp?=null

        val track = extras?.getParcelable<TrackDto>(App.trackKey)
        if (track != null) {
            trackDtoApp = TrackDtoApp(
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
        }

        return trackDtoApp
    }

}