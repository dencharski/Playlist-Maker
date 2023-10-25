package com.example.playlistmaker.audio_player_activity.data

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.audio_player_activity.data.dto.AudioPlayerViewState
import com.example.playlistmaker.audio_player_activity.domain.api.AudioPlayerRepository
import com.example.playlistmaker.audio_player_activity.ui.AudioPlayerActivity
import com.example.playlistmaker.audio_player_activity.ui.AudioPlayerViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerRepositoryImpl(private val mediaPlayer: MediaPlayer) : AudioPlayerRepository {


    private var playerState = STATE_DEFAULT

    companion object {
        const val STATE_DEFAULT = 0
        const val STATE_PREPARED = 1
        const val STATE_PLAYING = 2
        const val STATE_PAUSED = 3

    }

    override fun setTrack(previewUrl: String) {

        preparePlayer(previewUrl)

    }

    private fun preparePlayer(previewUrl: String) {

        mediaPlayer.reset()
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = STATE_PREPARED
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playerState = STATE_PLAYING
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playerState = STATE_PAUSED
    }

    override fun playbackControl(): Int {

        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
        return playerState
    }

    override fun onPause() {
        pausePlayer()
    }

    override fun getMediaPlayerCurrentPosition(): Int {
        return mediaPlayer.currentPosition ?: 0
    }
}