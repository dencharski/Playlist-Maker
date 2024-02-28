package com.example.playlistmaker.mediateka.domain.models

import com.example.playlistmaker.main.domain.models.TrackDtoApp

sealed class SelectedTracksViewState {

    object ListSelectedTracksEmpty : SelectedTracksViewState()
    data class ListSelectedTracks(val list:List<TrackDtoApp>) : SelectedTracksViewState()
}