package com.example.playlistmaker.mediateka.domain.api

import com.example.playlistmaker.main.domain.models.TrackApp
import kotlinx.coroutines.flow.Flow

interface SelectedTrackInteractor {
    suspend fun insertOneTrack(track: TrackApp)
    suspend fun deleteOneTrack(trackId: TrackApp)
    suspend fun getTracks(): Flow<List<TrackApp>>
}