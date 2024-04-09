package com.example.playlistmaker.mediateka.domain.models

import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.main.domain.models.TrackDtoApp

sealed class PlayListsViewState {
    object ListOfPlayListsIsEmpty : PlayListsViewState()
    data class ListOfPlayLists(val list:List<PlayList>) : PlayListsViewState()
}