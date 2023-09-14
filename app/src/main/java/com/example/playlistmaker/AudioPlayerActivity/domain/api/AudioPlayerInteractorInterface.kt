package com.example.playlistmaker.AudioPlayerActivity.domain.api

import android.content.Intent
import android.media.MediaPlayer
import com.example.playlistmaker.TrackDtoApp

interface AudioPlayerInteractorInterface {
    fun getDataExtrasTrack(intent: Intent): TrackDtoApp?

    fun getPlayer(): MediaPlayer
}