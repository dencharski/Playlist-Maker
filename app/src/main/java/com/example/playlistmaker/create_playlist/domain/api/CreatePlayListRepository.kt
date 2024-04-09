package com.example.playlistmaker.create_playlist.domain.api

import com.example.playlistmaker.mediateka.data.db.PlayListEntity

interface CreatePlayListRepository {
    suspend fun createPlaylist(playList:PlayListEntity)
}