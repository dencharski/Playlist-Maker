package com.example.playlistmaker.main.domain.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.Parceler
import java.text.SimpleDateFormat
import java.util.Locale

@Parcelize
data class TrackDtoApp(
    val trackId: Long,
    val trackName: String = "",
    val artistName: String = "",
    val trackTimeMillis: String = "",
    val artworkUrl100: String = "",
    val collectionName: String = "",
    val releaseDate: String = "",
    val primaryGenreName: String = "",
    val country: String = "",
    val previewUrl: String = "",
    var isFavorite: Boolean = false
) : Parcelable {

    val artworkUrl512
        get() = artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")

    fun getTrackTime(): String? {
        try {
            return SimpleDateFormat(
                "mm:ss",
                Locale.getDefault()
            ).format(trackTimeMillis.toLongOrNull())
        } catch (e: Exception) {
            return trackTimeMillis
        }
    }
}


