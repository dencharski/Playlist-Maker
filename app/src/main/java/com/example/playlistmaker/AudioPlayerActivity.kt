package com.example.playlistmaker

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.SearchActivity.SearchActivity
import com.example.playlistmaker.databinding.ActivityAudioplayerBinding
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivityAudioplayerBinding
    lateinit var track: Track

    private var mediaPlayer = MediaPlayer()
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
        private const val teg = "SearchActivity"
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAudioplayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Log.d(teg, "onCreate")
        binding.imageButtonPlayTrack.isEnabled = false

        binding.imageViewBackArrow.setOnClickListener {
            finish()
        }

        val extras = intent.extras
        if (extras != null) {
            track = extras.getParcelable<Track>(SearchActivity.trackKey)!!
            setViews()
        }

        preparePlayer()

        binding.imageButtonPlayTrack.setOnClickListener {
            playbackControl()
        }

        binding.imageButtonPauseTrack.setOnClickListener {
            playbackControl()
        }

    }

    private fun preparePlayer() {
        Log.d(teg, "track.previewUrl = ${track.previewUrl}")
        if (track.previewUrl.isNotEmpty()) {

            mediaPlayer.setDataSource(track.previewUrl)

            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                binding.imageButtonPlayTrack.isEnabled = true
                playerState = STATE_PREPARED
            }
            mediaPlayer.setOnCompletionListener {
                binding.imageButtonPlayTrack.visibility = View.VISIBLE
                binding.imageButtonPauseTrack.visibility=View.INVISIBLE
                playerState = STATE_PREPARED

                stopTimer()
                binding.textViewTrackTimeNowPlay.text = ZERO_VAL
            }
        }

    }

    private fun startPlayer() {
        mediaPlayer.start()
        binding.imageButtonPlayTrack.visibility = View.INVISIBLE
        binding.imageButtonPauseTrack.visibility = View.VISIBLE
        playerState = STATE_PLAYING

        handler.postDelayed(runnable, REFRESH_LIST_DELAY_MILLIS)

    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        binding.imageButtonPlayTrack.visibility = View.VISIBLE
        binding.imageButtonPauseTrack.visibility = View.INVISIBLE
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

    private fun getCoverArtwork() = track.artworkUrl512


    private fun setViews() {
        Glide.with(this)
            .load(getCoverArtwork())
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(cornerRadius, this)))
            .into(binding.imageViewArtworkUrl)



        binding.textViewTrackName.text = track.trackName
        binding.textViewArtistName.text = track.artistName
        binding.textViewTrackTime.text = track.getTrackTime()
        binding.textViewCollectionName.text = track.collectionName
        binding.textViewReleaseDate.text = track.releaseDate
        binding.textViewPrimaryGenreName.text = track.primaryGenreName
        binding.textViewCountry.text = track.country


    }

    private fun refreshTimeNowPlay() {
        binding.textViewTrackTimeNowPlay.text = SimpleDateFormat(
            "mm:ss",
            Locale.getDefault()
        ).format(mediaPlayer.currentPosition)
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
        mediaPlayer.release()
        stopTimer()
    }
}