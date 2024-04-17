package com.example.playlistmaker.audio_player.domain.api

import com.example.playlistmaker.create_playlist.domain.models.PlayList
import kotlinx.coroutines.flow.Flow

interface AudioPlayerPlayListRepository {
    suspend fun getListOfPlayLists(): Flow<List<PlayList>>

    suspend fun insertTrackIdInPlayList(playList:PlayList, trackId:Long)

    fun checkTrackIdInPlayList(playList:PlayList, trackId:Long):Boolean
}