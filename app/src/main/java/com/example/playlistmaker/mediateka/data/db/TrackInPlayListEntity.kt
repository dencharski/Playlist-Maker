package com.example.playlistmaker.mediateka.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "track_in_playlist_table")
data class TrackInPlayListEntity(
    @PrimaryKey
    val trackId: String,
    val trackName: String,
    val artistName: String ,
    val trackTimeMillis: String ,
    val artworkUrl100: String ,
    val collectionName: String ,
    val releaseDate: String ,
    val primaryGenreName: String ,
    val country: String ,
    val previewUrl:String)
