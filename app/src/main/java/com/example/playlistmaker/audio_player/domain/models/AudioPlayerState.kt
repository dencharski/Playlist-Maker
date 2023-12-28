package com.example.playlistmaker.audio_player.domain.models

class AudioPlayerState {
    companion object{
        const val STATE_DEFAULT_COMPLETED = 0
        const val STATE_PREPARED = 1
        const val STATE_PLAYING = 2
        const val STATE_PAUSED = 3
    }
}