package com.example.playlistmaker.mediateka.domain.api

import com.example.playlistmaker.mediateka.data.db.TrackEntity

interface AddTrackInPlayListRepository {
    suspend fun addTrackInPlayList(track:TrackEntity)
}