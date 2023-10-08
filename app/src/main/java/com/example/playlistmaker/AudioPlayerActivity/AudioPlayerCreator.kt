package com.example.playlistmaker.AudioPlayerActivity

import com.example.playlistmaker.AudioPlayerActivity.domain.api.AudioPlayerInteractorInterface
import com.example.playlistmaker.AudioPlayerActivity.domain.api.AudioPlayerRepositoryInterface
import com.example.playlistmaker.AudioPlayerActivity.domain.impl.AudioPlayerInteractorImpl
import com.example.playlistmaker.AudioPlayerActivity.data.AudioPlayerRepositoryImpl


object AudioPlayerCreator {
    fun getAudioPlayerInteractor(): AudioPlayerInteractorInterface {
        return AudioPlayerInteractorImpl(
            getAudioPlayerRepository()
        )
    }

    private fun getAudioPlayerRepository(): AudioPlayerRepositoryInterface {
        return AudioPlayerRepositoryImpl()
    }


}