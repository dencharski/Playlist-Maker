package com.example.playlistmaker.mediateka.domain.models

data class TrackMediateka(
    val trackId: Long,
    val trackName: String = "",
    val artistName: String = "",
    val trackTimeMillis: String = "",
    val artworkUrl100: String = "",
    val collectionName: String = "",
    val releaseDate: String = "",
    val primaryGenreName: String = "",
    val country: String = "",
    val previewUrl:String ="",
    var isFavorite: Boolean = false
)
