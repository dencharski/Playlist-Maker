package com.example.playlistmaker.mediateka.domain.api

import com.example.playlistmaker.mediateka.data.db.TrackEntity
import kotlinx.coroutines.flow.Flow

interface SelectedTracksRepository {
    suspend fun insertOneTrack(track: TrackEntity)
    suspend fun deleteOneTrack(trackId: TrackEntity)
    suspend fun getTracks(): Flow<List<TrackEntity>>

}