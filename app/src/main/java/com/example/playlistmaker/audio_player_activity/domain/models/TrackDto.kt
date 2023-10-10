package com.example.playlistmaker.audio_player_activity.domain.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Locale

@Parcelize
data class TrackDto(
    val trackId: Long,
    val trackName: String = "",
    val artistName: String = "",
    val trackTimeMillis: String = "",
    val artworkUrl100: String = "",
    val collectionName: String = "",
    val releaseDate: String = "",
    val primaryGenreName: String = "",
    val country: String = "",
    val previewUrl:String =""
): Parcelable {

    val artworkUrl512
        get() = artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")



    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    fun getTrackTime(): String? {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis.toLongOrNull())
    }

    companion object : Parceler<TrackDto> {

        override fun TrackDto.write(parcel: Parcel, flags: Int) {
            parcel.writeLong(trackId)
            parcel.writeString(trackName)
            parcel.writeString(artistName)
            parcel.writeString(trackTimeMillis)
            parcel.writeString(artworkUrl100)
            parcel.writeString(collectionName)
            parcel.writeString(releaseDate)
            parcel.writeString(primaryGenreName)
            parcel.writeString(country)
            parcel.writeString(previewUrl)
        }

        override fun create(parcel: Parcel): TrackDto {
            return TrackDto(parcel)
        }
    }

}