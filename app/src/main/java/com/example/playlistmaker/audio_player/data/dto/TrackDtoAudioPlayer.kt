package com.example.playlistmaker.audio_player.data.dto

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import okio.IOException
import java.text.SimpleDateFormat
import java.util.Locale

@Parcelize
data class TrackDtoAudioPlayer(
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
    var isFavorite: Boolean =false
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
        parcel.readString().toString(),
        parcel.readBoolean()
    ) {
    }

    fun getTrackTime(): String? {
        Log.d("teg", "trackTimeMills = ${trackTimeMillis}")
        try {
            return SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis.toLongOrNull())
        }catch (e :Exception){
            return trackTimeMillis
        }

    }

    companion object : Parceler<TrackDtoAudioPlayer> {

        override fun TrackDtoAudioPlayer.write(parcel: Parcel, flags: Int) {
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
            parcel.writeBoolean(isFavorite)
        }

        override fun create(parcel: Parcel): TrackDtoAudioPlayer {
            return TrackDtoAudioPlayer(parcel)
        }
    }

}