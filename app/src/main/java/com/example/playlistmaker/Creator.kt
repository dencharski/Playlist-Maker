package com.example.playlistmaker

import com.example.playlistmaker.AudioPlayerActivity.data.AudioPlayerDataExtrasRepositoryImpl
import com.example.playlistmaker.AudioPlayerActivity.domain.api.AudioPlayerInteractorInterface
import com.example.playlistmaker.AudioPlayerActivity.domain.api.AudioPlayerRepositoryInterface
import com.example.playlistmaker.AudioPlayerActivity.domain.impl.AudioPlayerInteractorImpl
import com.example.playlistmaker.AudioPlayerActivity.data.AudioPlayerRepositoryImpl
import com.example.playlistmaker.AudioPlayerActivity.domain.api.AudioPlayerDataExtrasRepositoryInterface


object Creator {
    fun getAudioPlayerInteractor(): AudioPlayerInteractorInterface {
        return AudioPlayerInteractorImpl(
            getAudioPlayerRepository(),
            getAudioPlayerDataExtrasRepository()
        )
    }

    private fun getAudioPlayerRepository(): AudioPlayerRepositoryInterface {
        return AudioPlayerRepositoryImpl()
    }

    private fun getAudioPlayerDataExtrasRepository(): AudioPlayerDataExtrasRepositoryInterface {
        return AudioPlayerDataExtrasRepositoryImpl()
    }
}