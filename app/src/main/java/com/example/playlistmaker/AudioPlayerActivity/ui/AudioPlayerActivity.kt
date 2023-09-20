package com.example.playlistmaker.AudioPlayerActivity.ui

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.AudioPlayerActivity.domain.api.AudioPlayerInteractorInterface
import com.example.playlistmaker.Creator
import com.example.playlistmaker.R
import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.databinding.ActivityAudioplayerBinding
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerActivity : AppCompatActivity() {

    private var binding: ActivityAudioplayerBinding? = null
    private var track: TrackDtoApp? = null

    private var mediaPlayer: MediaPlayer? = null
    private var audioPlayerInteractorImpl: AudioPlayerInteractorInterface? = null
    private val handler = Handler(Looper.getMainLooper())

    private val runnable = object : Runnable {
        override fun run() {
            refreshTimeNowPlay()
            handler.postDelayed(this, REFRESH_LIST_DELAY_MILLIS)
        }
    }

    private var playerState = STATE_DEFAULT

    companion object {
        const val cornerRadius: Float = 8f
        private const val REFRESH_LIST_DELAY_MILLIS = 500L

        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3

        private const val ZERO_VAL = "00:00"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAudioplayerBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)


        binding?.imageButtonPlayTrack?.isEnabled = false
        binding?.imageViewBackArrow?.setOnClickListener {
            finish()
        }

        audioPlayerInteractorImpl = Creator.getAudioPlayerInteractor()

        mediaPlayer = audioPlayerInteractorImpl?.getPlayer()

        track = audioPlayerInteractorImpl?.getDataExtrasTrack(intent)

        if (track != null) {
            setViews()
        }

        preparePlayer()

        binding?.imageButtonPlayTrack?.setOnClickListener {
            playbackControl()
        }

        binding?.imageButtonPauseTrack?.setOnClickListener {
            playbackControl()
        }

    }

    private fun preparePlayer() {

        if (track?.previewUrl?.isNotEmpty() == true) {

            mediaPlayer?.setDataSource(track?.previewUrl)

            mediaPlayer?.prepareAsync()
            mediaPlayer?.setOnPreparedListener {
                binding?.imageButtonPlayTrack?.isEnabled = true
                playerState = STATE_PREPARED
            }
            mediaPlayer?.setOnCompletionListener {
                binding?.imageButtonPlayTrack?.isVisible = true
                binding?.imageButtonPauseTrack?.isVisible = false
                playerState = STATE_PREPARED

                stopTimer()
                binding?.textViewTrackTimeNowPlay?.text = ZERO_VAL
            }
        }

    }

    private fun startPlayer() {
        mediaPlayer?.start()
        binding?.imageButtonPlayTrack?.isVisible= false
        binding?.imageButtonPauseTrack?.isVisible = true
        playerState = STATE_PLAYING

        handler.postDelayed(runnable, REFRESH_LIST_DELAY_MILLIS)

    }

    private fun pausePlayer() {
        mediaPlayer?.pause()
        binding?.imageButtonPlayTrack?.isVisible = true
        binding?.imageButtonPauseTrack?.isVisible = false
        playerState = STATE_PAUSED

        stopTimer()
    }

    private fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    private fun stopTimer() {
        handler.removeCallbacks(runnable)
    }

    private fun getCoverArtwork() = track?.artworkUrl512

    private fun setViews() {
        binding?.imageViewArtworkUrl?.let {
            Glide.with(this)
                .load(getCoverArtwork())
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .transform(RoundedCorners(dpToPx(cornerRadius, this)))
                .into(it)
        }



        binding?.textViewTrackName?.text = track?.trackName
        binding?.textViewArtistName?.text = track?.artistName
        binding?.textViewTrackTime?.text = track?.trackTimeMillis
        binding?.textViewCollectionName?.text = track?.collectionName
        binding?.textViewReleaseDate?.text = track?.releaseDate
        binding?.textViewPrimaryGenreName?.text = track?.primaryGenreName
        binding?.textViewCountry?.text = track?.country


    }

    private fun refreshTimeNowPlay() {
        binding?.textViewTrackTimeNowPlay?.text = SimpleDateFormat(
            "mm:ss",
            Locale.getDefault()
        ).format(mediaPlayer?.currentPosition)
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        stopTimer()
    }
}