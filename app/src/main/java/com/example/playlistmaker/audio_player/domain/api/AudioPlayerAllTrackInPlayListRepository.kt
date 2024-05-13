package com.example.playlistmaker.audio_player.domain.api

import com.example.playlistmaker.main.domain.models.TrackApp
import kotlinx.coroutines.flow.Flow

interface AudioPlayerAllTrackInPlayListRepository {
    suspend fun addTrackInPlayListTable(track:TrackApp)
    suspend fun getAllTracksInPlayList(listOfTrackId:List<Long>): Flow<List<TrackApp>>
    suspend fun deleteOneTrackFromPlayList(trackId: Long)
}