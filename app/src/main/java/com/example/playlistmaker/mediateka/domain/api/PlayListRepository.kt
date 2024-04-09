package com.example.playlistmaker.mediateka.domain.api

import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.mediateka.data.db.PlayListEntity
import kotlinx.coroutines.flow.Flow

interface PlayListRepository {
    suspend fun getListOfPlayLists(): Flow<List<PlayListEntity>>
    suspend fun insertPlaylist(playList: PlayListEntity)
    suspend fun createPlaylist(playList:PlayListEntity)
}