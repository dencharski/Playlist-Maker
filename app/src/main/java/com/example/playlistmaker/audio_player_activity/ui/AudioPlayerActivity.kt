package com.example.playlistmaker.audio_player_activity.ui

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.audio_player_activity.data.dto.AudioPlayerViewState
import com.example.playlistmaker.databinding.ActivityAudioplayerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerActivity : AppCompatActivity() {

    private var binding: ActivityAudioplayerBinding? = null

    private val audioPlayerViewModel by viewModel<AudioPlayerViewModel>()


    companion object {
        private const val cornerRadius: Float = 8f
        private const val ZERO_VAL = "00:00"
        private const val teg = "audioplayer"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAudioplayerBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        audioPlayerViewModel.setDataExtrasTrack(intent.extras)

        observeValues()

        binding?.imageButtonPlayTrack?.isEnabled = false

        binding?.imageViewBackArrow?.setOnClickListener {
            finish()
        }

        binding?.imageButtonPlayTrack?.setOnClickListener {

            audioPlayerViewModel.playbackControl()
        }

        binding?.imageButtonPauseTrack?.setOnClickListener {

            audioPlayerViewModel.playbackControl()
        }

    }


    private fun observeValues() {

        audioPlayerViewModel.audioPlayerViewState.observe(this) {
            when (it) {
                is AudioPlayerViewState.Error -> {
                    Log.d(teg, "error view state")
                }

                is AudioPlayerViewState.Play -> {
                    binding?.imageButtonPlayTrack?.visibility = View.INVISIBLE
                    binding?.imageButtonPauseTrack?.visibility = View.VISIBLE
                }

                is AudioPlayerViewState.Pause -> {
                    binding?.imageButtonPlayTrack?.visibility = View.VISIBLE
                    binding?.imageButtonPauseTrack?.visibility = View.INVISIBLE
                }

                is AudioPlayerViewState.Track -> {
                    setViews(it.track)
                    binding?.imageButtonPlayTrack?.isEnabled = true

                    binding?.imageButtonPlayTrack?.visibility = View.VISIBLE
                    binding?.imageButtonPauseTrack?.visibility = View.INVISIBLE

                    binding?.textViewTrackTimeNowPlay?.text = ZERO_VAL
                }

                is AudioPlayerViewState.CurrentPosition -> {
                    binding?.textViewTrackTimeNowPlay?.text = it.currentPosition
                }

                else -> {

                }
            }


        }

    }

    private fun getCoverArtwork(track: TrackDtoApp) = track.artworkUrl512

    private fun setViews(track: TrackDtoApp) {
        binding?.imageViewArtworkUrl?.let {
            Glide.with(this)
                .load(getCoverArtwork(track))
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .transform(RoundedCorners(dpToPx(cornerRadius, this)))
                .into(it)
        }

        binding?.textViewTrackName?.text = track.trackName
        binding?.textViewArtistName?.text = track.artistName
        binding?.textViewTrackTime?.text = track.trackTimeMillis
        binding?.textViewCollectionName?.text = track.collectionName
        binding?.textViewReleaseDate?.text = track.releaseDate
        binding?.textViewPrimaryGenreName?.text = track.primaryGenreName
        binding?.textViewCountry?.text = track.country


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
        audioPlayerViewModel.onPause()

    }

    override fun onDestroy() {
        super.onDestroy()
        audioPlayerViewModel.onDestroy()

    }
}