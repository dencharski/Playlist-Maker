package com.example.playlistmaker.audio_player.data.impl

import android.media.MediaPlayer
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerRepository
import com.example.playlistmaker.audio_player.domain.models.AudioPlayerState

class AudioPlayerRepositoryImpl(private val mediaPlayer: MediaPlayer) : AudioPlayerRepository {

    private var playerState = AudioPlayerState.STATE_DEFAULT_COMPLETED

    override fun setTrack(previewUrl: String) {
        preparePlayer(previewUrl)
    }

    private fun preparePlayer(previewUrl: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = AudioPlayerState.STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = AudioPlayerState.STATE_DEFAULT_COMPLETED
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playerState = AudioPlayerState.STATE_PLAYING
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playerState = AudioPlayerState.STATE_PAUSED
    }

    override fun playbackControl(): Int {

        when (playerState) {
            AudioPlayerState.STATE_PLAYING -> {
                pausePlayer()
            }

            AudioPlayerState.STATE_PREPARED,AudioPlayerState.STATE_PAUSED -> {
                startPlayer()
            }

            AudioPlayerState.STATE_DEFAULT_COMPLETED -> {
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