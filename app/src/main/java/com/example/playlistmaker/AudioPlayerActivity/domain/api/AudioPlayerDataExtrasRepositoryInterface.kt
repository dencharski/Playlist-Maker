package com.example.playlistmaker.AudioPlayerActivity.domain.api

import android.content.Intent
import com.example.playlistmaker.AudioPlayerActivity.domain.models.TrackDto

interface AudioPlayerDataExtrasRepositoryInterface {
    fun getDataExtrasTrack(intent: Intent): TrackDto?
}