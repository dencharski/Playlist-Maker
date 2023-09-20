package com.example.playlistmaker.AudioPlayerActivity.domain.api

import android.media.MediaPlayer

interface AudioPlayerRepositoryInterface {
    fun getPlayer():MediaPlayer
}