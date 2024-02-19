package com.example.playlistmaker.mediateka.domain.models

import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.audio_player.domain.models.AudioPlayerViewState

sealed class SelectedTracksViewState {

    object ListSelectedTracksEmpty : SelectedTracksViewState()
    data class ListSelectedTracks(val list:List<TrackDtoApp>) : SelectedTracksViewState()
}