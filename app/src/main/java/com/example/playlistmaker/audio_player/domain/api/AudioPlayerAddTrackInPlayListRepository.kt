package com.example.playlistmaker.audio_player.domain.api

import com.example.playlistmaker.main.domain.models.TrackApp

interface AudioPlayerAddTrackInPlayListRepository {
    suspend fun addTrackInPlayListTable(track:TrackApp)
}