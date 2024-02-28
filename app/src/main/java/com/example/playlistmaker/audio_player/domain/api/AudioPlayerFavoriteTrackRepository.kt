package com.example.playlistmaker.audio_player.domain.api

import com.example.playlistmaker.mediateka.data.db.TrackEntity
import kotlinx.coroutines.flow.Flow

interface AudioPlayerFavoriteTrackRepository {
    suspend fun insertOneTrack(track: TrackEntity)
    suspend fun deleteOneTrack(trackId:TrackEntity)

    suspend fun getTracksIds(): List<String>
    suspend fun getTracks(): Flow<List<TrackEntity>>
}