package com.example.playlistmaker.AudioPlayerActivity.domain.impl

import android.media.MediaPlayer
import com.example.playlistmaker.AudioPlayerActivity.domain.api.AudioPlayerInteractorInterface
import com.example.playlistmaker.AudioPlayerActivity.domain.api.AudioPlayerRepositoryInterface

class AudioPlayerInteractorImpl(
    private val playerRepositoryImpl: AudioPlayerRepositoryInterface
   ) : AudioPlayerInteractorInterface {


    override fun getPlayer(): MediaPlayer {
        return playerRepositoryImpl.getPlayer()
    }

}