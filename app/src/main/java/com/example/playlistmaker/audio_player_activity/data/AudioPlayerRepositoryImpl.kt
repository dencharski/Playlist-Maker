package com.example.playlistmaker.audio_player_activity.data

import android.media.MediaPlayer
import android.util.Log
import com.example.playlistmaker.App
import com.example.playlistmaker.audio_player_activity.domain.api.AudioPlayerRepository

class AudioPlayerRepositoryImpl(private val mediaPlayer: MediaPlayer) : AudioPlayerRepository {

    private var playerState = App.STATE_DEFAULT_COMPLETED

    override fun setTrack(previewUrl: String) {
        preparePlayer(previewUrl)
    }

    private fun preparePlayer(previewUrl: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = App.STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = App.STATE_DEFAULT_COMPLETED
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playerState = App.STATE_PLAYING
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playerState = App.STATE_PAUSED
    }

    override fun playbackControl(): Int {

        when (playerState) {
            App.STATE_PLAYING -> {
                pausePlayer()
            }

           App.STATE_PREPARED,App.STATE_PAUSED -> {
                startPlayer()
            }

            App.STATE_DEFAULT_COMPLETED -> {
                pausePlayer()
            }

        }
        return playerState
    }

    override fun onPause() {
        pausePlayer()
    }

    override fun getMediaPlayerCurrentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    override fun getIsPlayingCompleted(): Int {
        return playerState
    }
}