package com.example.playlistmaker.AudioPlayerActivity.data

import android.app.Application
import android.content.Intent
import com.example.playlistmaker.App
import com.example.playlistmaker.AudioPlayerActivity.domain.api.AudioPlayerDataExtrasRepositoryInterface
import com.example.playlistmaker.AudioPlayerActivity.domain.models.TrackDto
import com.example.playlistmaker.SearchActivity.SearchActivity

class AudioPlayerDataExtrasRepositoryImpl : AudioPlayerDataExtrasRepositoryInterface {
    override fun getDataExtrasTrack(intent: Intent): TrackDto? {
        var track: TrackDto? = null
        if (intent.extras != null) {
             track = intent.extras?.getParcelable<TrackDto>(App.trackKey)
        }

        return track
    }

}