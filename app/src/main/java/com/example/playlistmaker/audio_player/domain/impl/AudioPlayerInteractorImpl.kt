package com.example.playlistmaker.audio_player.domain.impl

import com.example.playlistmaker.audio_player.domain.api.AudioPlayerInteractor
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerRepository

class AudioPlayerInteractorImpl(
    private val playerRepositoryImpl: AudioPlayerRepository
) : AudioPlayerInteractor {
    override fun setTrack(previewUrl: String) {
        playerRepositoryImpl.setTrack(previewUrl)
    }

    override fun playbackControl(): Int {
        return playerRepositoryImpl.playbackControl()
    }

    override fun onPause() {
        playerRepositoryImpl.onPause()
    }

    override fun getMediaPlayerCurrentPosition(): Int {
        return playerRepositoryImpl.getMediaPlayerCurrentPosition()
    }

    override fun getIsPlayingCompleted(): Int {
        return playerRepositoryImpl.getIsPlayingCompleted()
    }

}