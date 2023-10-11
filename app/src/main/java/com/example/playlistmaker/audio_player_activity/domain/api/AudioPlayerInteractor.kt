package com.example.playlistmaker.audio_player_activity.domain.api

import android.media.MediaPlayer

interface AudioPlayerInteractor {
    fun getPlayer(): MediaPlayer
}