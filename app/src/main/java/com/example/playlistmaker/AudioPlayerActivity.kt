package com.example.playlistmaker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.SearchActivity.SearchActivity
import com.example.playlistmaker.SearchActivity.TrackListAdapter
import com.example.playlistmaker.databinding.ActivityAudioplayerBinding
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivityAudioplayerBinding
    lateinit var track: Track

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

            track = Track(
                extras.getString(SearchActivity.trackId.toString())?.toLong() ?: 0,
                extras.getString(SearchActivity.trackName.toString(),"").toString(),
                extras.getString(SearchActivity.artistName, "").toString(),
                extras.getString(SearchActivity.trackTimeMillis.toString(),"").toString(),
                extras.getString(SearchActivity.artworkUrl100.toString(),"").toString(),
                extras.getString(SearchActivity.collectionName.toString(),"").toString(),
                extras.getString(SearchActivity.releaseDate.toString(),"").toString(),
                extras.getString(SearchActivity.primaryGenreName.toString(),"").toString(),
                extras.getString(SearchActivity.country.toString(),"").toString()
            )

            setViews()
        }


    }

    private fun getCoverArtwork() = track.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")

    private fun setViews(){
        Glide.with(this)
            .load(getCoverArtwork())
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .into(binding.imageViewArtworkUrl)



        binding.textViewTrackName.text=track.trackName
        binding.textViewArtistName.text=track.artistName

        binding.textViewTrackTime.text=track.trackTimeMillis
        val date=SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis.toLongOrNull())

        binding.textViewTrackTime.text=date.toString()
        binding.textViewCollectionName.text=track.collectionName
        binding.textViewReleaseDate.text=track.releaseDate
        binding.textViewPrimaryGenreName.text=track.primaryGenreName
        binding.textViewCountry.text=track.country
    }

}