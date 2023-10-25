package com.example.playlistmaker.audio_player_activity.domain.api

import android.media.MediaPlayer
import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.audio_player_activity.data.dto.AudioPlayerViewState

interface AudioPlayerInteractor {


    fun setTrack(previewUrl:String)
    fun playbackControl():Int
    fun onPause()
    fun getMediaPlayerCurrentPosition():Int
}