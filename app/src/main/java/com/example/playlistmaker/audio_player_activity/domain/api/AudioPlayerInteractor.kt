package com.example.playlistmaker.audio_player_activity.domain.api

interface AudioPlayerInteractor {


    fun setTrack(previewUrl:String)
    fun playbackControl():Int
    fun onPause()
    fun getMediaPlayerCurrentPosition():Int
    fun getIsPlayingCompleted():Int
}