package com.example.playlistmaker.mediateka.domain.api

import com.example.playlistmaker.main.domain.models.TrackApp
import com.example.playlistmaker.mediateka.data.db.TrackEntity
import kotlinx.coroutines.flow.Flow

interface SelectedTracksRepository {
    suspend fun insertOneTrack(track: TrackApp)
    suspend fun deleteOneTrack(trackId: TrackApp)
    suspend fun getTracks(): Flow<List<TrackApp>>

}