package com.example.playlistmaker.audio_player_activity.domain.impl

import android.media.MediaPlayer
import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.audio_player_activity.data.dto.AudioPlayerViewState
import com.example.playlistmaker.audio_player_activity.domain.api.AudioPlayerInteractor
import com.example.playlistmaker.audio_player_activity.domain.api.AudioPlayerRepository

class AudioPlayerInteractorImpl(
    private val playerRepositoryImpl: AudioPlayerRepository
   ) : AudioPlayerInteractor {



    override fun setTrack(previewUrl:String) {
        playerRepositoryImpl.setTrack(previewUrl)
    }

    override fun playbackControl():Int {
        return playerRepositoryImpl.playbackControl()
    }

    override fun onPause() {
        playerRepositoryImpl.onPause()
    }


    override fun getMediaPlayerCurrentPosition(): Int {
        return playerRepositoryImpl.getMediaPlayerCurrentPosition()
    }

}