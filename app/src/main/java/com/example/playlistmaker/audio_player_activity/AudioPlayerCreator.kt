package com.example.playlistmaker.audio_player_activity

import com.example.playlistmaker.audio_player_activity.domain.api.AudioPlayerInteractor
import com.example.playlistmaker.audio_player_activity.domain.api.AudioPlayerRepository
import com.example.playlistmaker.audio_player_activity.domain.impl.AudioPlayerInteractorImpl
import com.example.playlistmaker.audio_player_activity.data.AudioPlayerRepositoryImpl


object AudioPlayerCreator {
    fun getAudioPlayerInteractor(): AudioPlayerInteractor {
        return AudioPlayerInteractorImpl(
            getAudioPlayerRepository()
        )
    }

    private fun getAudioPlayerRepository(): AudioPlayerRepository {
        return AudioPlayerRepositoryImpl()
    }


}