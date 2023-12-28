package com.example.playlistmaker.audio_player.domain.api

interface AudioPlayerRepository {


    fun setTrack(previewUrl:String)
    fun playbackControl():Int
    fun onPause()
    fun getMediaPlayerCurrentPosition():Int
    fun getIsPlayingCompleted():Int
}