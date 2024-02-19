package com.example.playlistmaker.audio_player.domain.api

import com.example.playlistmaker.TrackDtoApp
import kotlinx.coroutines.flow.Flow

interface AudioPlayerFavoriteTrackInteractor {
    suspend fun insertOneTrack(track:TrackDtoApp)
    suspend fun deleteOneTrack(trackId:TrackDtoApp)
    suspend fun getTracksIds(): List<String>
    suspend fun getTracks(): Flow<List<TrackDtoApp>>
}