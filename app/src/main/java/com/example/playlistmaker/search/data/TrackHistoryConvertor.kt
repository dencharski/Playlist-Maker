package com.example.playlistmaker.search.data

import com.example.playlistmaker.main.domain.models.TrackDtoApp
import com.example.playlistmaker.search.data.dto.TrackHistoryDto

class TrackHistoryConvertor {
    fun map(track: TrackDtoApp): TrackHistoryDto {
        return TrackHistoryDto(
            trackId = track.trackId,
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

    fun map(track: TrackHistoryDto): TrackDtoApp {
        return TrackDtoApp(
            trackId = track.trackId,
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl,
            isFavorite = track.isFavorite)
    }
}