package com.example.playlistmaker.mediateka.domain.models

import com.example.playlistmaker.create_playlist.domain.models.PlayList

sealed class PlayListsViewState {
    object ListOfPlayListsIsEmpty : PlayListsViewState()
    data class ListOfPlayLists(val list:List<PlayList>) : PlayListsViewState()
}