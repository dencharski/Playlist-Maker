package com.example.playlistmaker.create_playlist.data

import com.example.playlistmaker.main.domain.models.TrackApp
import com.example.playlistmaker.mediateka.data.db.TrackEntity
import com.example.playlistmaker.mediateka.data.db.TrackInPlayListEntity

class TrackInPlayListConvertor {
    fun map(track: TrackApp): TrackInPlayListEntity {
        return TrackInPlayListEntity(
            trackId = track.trackId.toString(),
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl
        )
    }

    fun map(track: TrackInPlayListEntity): TrackApp {
        return TrackApp(trackId = track.trackId.toLong(),
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl,
            isFavorite = false)
    }
}