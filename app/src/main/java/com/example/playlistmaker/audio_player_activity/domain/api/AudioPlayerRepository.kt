package com.example.playlistmaker.audio_player_activity.domain.api

interface AudioPlayerRepository {


    fun setTrack(previewUrl:String)
    fun playbackControl():Int
    fun onPause()
    fun getMediaPlayerCurrentPosition():Int
    fun getIsPlayingCompleted():Int
}