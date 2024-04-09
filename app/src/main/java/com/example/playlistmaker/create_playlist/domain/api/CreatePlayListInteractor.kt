package com.example.playlistmaker.create_playlist.domain.api

import com.example.playlistmaker.create_playlist.domain.models.PlayList

interface CreatePlayListInteractor {
    suspend fun createPlaylist(playList: PlayList)
}