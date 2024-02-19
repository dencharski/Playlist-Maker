package com.example.playlistmaker.audio_player.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.App
import com.example.playlistmaker.R
import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.audio_player.domain.models.AudioPlayerViewState
import com.example.playlistmaker.databinding.ActivityAudioplayerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AudioPlayerActivity : AppCompatActivity() {

    private var binding: ActivityAudioplayerBinding? = null
    private val audioPlayerViewModel by viewModel<AudioPlayerViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAudioplayerBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        audioPlayerViewModel.setDataExtrasTrack(intent.extras?.getParcelable(App.trackKey))

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

        binding?.imageButtonLikeItCheck?.setOnClickListener {
            audioPlayerViewModel.onFavoriteClicked()
        }

        binding?.imageButtonLikeItNotCheck?.setOnClickListener {
            audioPlayerViewModel.onFavoriteClicked()
        }

    }


    private fun observeValues() {

        audioPlayerViewModel.trackDtoApp.observe(this) {
            setViews(it)
            binding?.imageButtonPlayTrack?.isEnabled = true
            binding?.imageButtonPlayTrack?.visibility = View.VISIBLE
            binding?.imageButtonPauseTrack?.visibility = View.INVISIBLE
            binding?.textViewTrackTimeNowPlay?.text = ZERO_VAL

        }

        audioPlayerViewModel.audioPlayerViewState.observe(this) {
            when (it) {
                is AudioPlayerViewState.Error -> {
                    Log.d(tag, "error view state")
                }

                is AudioPlayerViewState.Play -> {
                    binding?.imageButtonPlayTrack?.visibility = View.INVISIBLE
                    binding?.imageButtonPauseTrack?.visibility = View.VISIBLE
                }

                is AudioPlayerViewState.Pause -> {
                    binding?.imageButtonPlayTrack?.visibility = View.VISIBLE
                    binding?.imageButtonPauseTrack?.visibility = View.INVISIBLE
                }


                is AudioPlayerViewState.CurrentPosition -> {
                    binding?.textViewTrackTimeNowPlay?.text = it.currentPosition
                }

                is AudioPlayerViewState.PlayCompleted -> {
                    audioPlayerViewModel.playbackControl()
                    binding?.textViewTrackTimeNowPlay?.text = ZERO_VAL
                }

                is AudioPlayerViewState.AddFavoriteClick -> {
                    if (it.isFavorite) {

                        binding?.imageButtonLikeItCheck?.visibility = View.VISIBLE
                        binding?.imageButtonLikeItNotCheck?.visibility = View.INVISIBLE
                    } else {

                        binding?.imageButtonLikeItCheck?.visibility = View.INVISIBLE
                        binding?.imageButtonLikeItNotCheck?.visibility = View.VISIBLE
                    }

                }

                else -> {
                    Log.d(tag, "smth wrong")
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

        if (track.isFavorite) {

            binding?.imageButtonLikeItCheck?.visibility = View.VISIBLE
            binding?.imageButtonLikeItNotCheck?.visibility = View.INVISIBLE
        } else {

            binding?.imageButtonLikeItCheck?.visibility = View.INVISIBLE
            binding?.imageButtonLikeItNotCheck?.visibility = View.VISIBLE
        }


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

    companion object {
        private const val cornerRadius: Float = 8f
        private const val ZERO_VAL = "00:00"
        private const val tag = "audioplayer"
    }
}