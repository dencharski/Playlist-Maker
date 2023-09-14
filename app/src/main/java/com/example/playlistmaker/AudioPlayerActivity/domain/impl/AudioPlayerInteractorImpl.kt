package com.example.playlistmaker.AudioPlayerActivity.domain.impl

import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import com.example.playlistmaker.AudioPlayerActivity.domain.api.AudioPlayerDataExtrasRepositoryInterface
import com.example.playlistmaker.AudioPlayerActivity.domain.api.AudioPlayerInteractorInterface
import com.example.playlistmaker.AudioPlayerActivity.domain.api.AudioPlayerRepositoryInterface
import com.example.playlistmaker.AudioPlayerActivity.domain.models.TrackDto
import com.example.playlistmaker.AudioPlayerActivity.ui.AudioPlayerActivity
import com.example.playlistmaker.SearchActivity.SearchActivity
import com.example.playlistmaker.TrackDtoApp

class AudioPlayerInteractorImpl(
    private val playerRepositoryImpl: AudioPlayerRepositoryInterface,
    private val playerDataExtrasRepositoryImpl: AudioPlayerDataExtrasRepositoryInterface
) : AudioPlayerInteractorInterface {
    override fun getDataExtrasTrack(intent: Intent): TrackDtoApp? {
        var trackDtoApp: TrackDtoApp? = null

        val track=playerDataExtrasRepositoryImpl.getDataExtrasTrack(intent)

        if (track != null) {
            trackDtoApp = TrackDtoApp(
                track.trackId,
                track.trackName,
                track.artistName,
                track.getTrackTime().toString(),
                track.artworkUrl100,
                track.collectionName,
                track.releaseDate,
                track.primaryGenreName,
                track.country,
                track.previewUrl
            )
        }
        return trackDtoApp
    }

    override fun getPlayer(): MediaPlayer {
        return playerRepositoryImpl.getPlayer()
    }

}