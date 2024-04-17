package com.example.playlistmaker.audio_player.domain.api

import com.example.playlistmaker.main.domain.models.TrackApp
import kotlinx.coroutines.flow.Flow

interface AudioPlayerSelectedTrackInteractor {
    suspend fun insertOneTrack(track: TrackApp)
    suspend fun deleteOneTrack(trackId: TrackApp)
    suspend fun getTracksIds(): List<String>
    suspend fun getTracks(): Flow<List<TrackApp>>
}