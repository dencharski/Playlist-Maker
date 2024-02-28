package com.example.playlistmaker.mediateka.domain.api

import com.example.playlistmaker.main.domain.models.TrackDtoApp
import kotlinx.coroutines.flow.Flow

interface SelectedTrackInteractor {
    suspend fun insertOneTrack(track: TrackDtoApp)
    suspend fun deleteOneTrack(trackId: TrackDtoApp)
    suspend fun getTracks(): Flow<List<TrackDtoApp>>
}