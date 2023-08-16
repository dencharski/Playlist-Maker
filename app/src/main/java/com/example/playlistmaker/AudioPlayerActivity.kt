package com.example.playlistmaker

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.util.TypedValue
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.SearchActivity.SearchActivity
import com.example.playlistmaker.databinding.ActivityAudioplayerBinding

class AudioPlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivityAudioplayerBinding
    lateinit var track: Track

    companion object {
        const val cornerRadius: Float = 8f
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAudioplayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.imageViewBackArrow.setOnClickListener {
            finish()
        }

        val extras = intent.extras
        if (extras != null) {

            track = extras.getParcelable<Track>(SearchActivity.trackKey)!!

            setViews()
        }


    }

    private fun getCoverArtwork() = track.artworkUrl512


    private fun setViews()
    {
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

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}