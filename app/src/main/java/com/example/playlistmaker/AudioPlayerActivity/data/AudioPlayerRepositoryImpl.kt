package com.example.playlistmaker.AudioPlayerActivity.data

import android.media.MediaPlayer
import com.example.playlistmaker.AudioPlayerActivity.domain.api.AudioPlayerRepositoryInterface

class AudioPlayerRepositoryImpl():AudioPlayerRepositoryInterface {
    override fun getPlayer(): MediaPlayer {
        return MediaPlayer()
    }
}