package com.example.playlistmaker.audio_player_activity.data

import android.media.MediaPlayer
import com.example.playlistmaker.audio_player_activity.domain.api.AudioPlayerRepository

class AudioPlayerRepositoryImpl():AudioPlayerRepository {
    override fun getPlayer(): MediaPlayer {
        return MediaPlayer()
    }
}