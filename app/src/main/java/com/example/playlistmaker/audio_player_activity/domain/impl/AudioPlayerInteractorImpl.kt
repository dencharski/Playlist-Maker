package com.example.playlistmaker.audio_player_activity.domain.impl

import android.media.MediaPlayer
import com.example.playlistmaker.audio_player_activity.domain.api.AudioPlayerInteractor
import com.example.playlistmaker.audio_player_activity.domain.api.AudioPlayerRepository

class AudioPlayerInteractorImpl(
    private val playerRepositoryImpl: AudioPlayerRepository
   ) : AudioPlayerInteractor {


    override fun getPlayer(): MediaPlayer {
        return playerRepositoryImpl.getPlayer()
    }

}