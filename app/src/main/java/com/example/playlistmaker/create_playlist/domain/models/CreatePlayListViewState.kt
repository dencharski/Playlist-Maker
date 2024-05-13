package com.example.playlistmaker.create_playlist.domain.models

sealed class CreatePlayListViewState {
    data class EditPlayList(val playList: PlayList) : CreatePlayListViewState()
}