package com.example.playlistmaker.mediateka.data


import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.mediateka.data.db.TrackEntity


class TrackDbConvertor {
    fun map(track: TrackDtoApp): TrackEntity {
        return TrackEntity(
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

    fun map(track: TrackEntity): TrackDtoApp {
        return TrackDtoApp(trackId = track.trackId.toLong(),
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl,
            isFavorite = true)
    }
}