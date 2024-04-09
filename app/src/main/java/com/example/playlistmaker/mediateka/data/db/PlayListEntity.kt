package com.example.playlistmaker.mediateka.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "playlist_table")
data class PlayListEntity(
    @PrimaryKey(autoGenerate = true)
    val playListId: Long?=null,
    val playListName: String,
    val playlistDescription: String,
    val playlistImageUri: String,
    val listOfTrackIds: String,
    val sizeOfTrackIdsList: String
)