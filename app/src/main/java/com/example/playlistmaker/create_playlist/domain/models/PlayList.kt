package com.example.playlistmaker.create_playlist.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayList(
    val playListId: Long?,
    var playListName: String,
    var playlistDescription: String,
    var playlistImageUri: String,
    var listOfTrackIds: MutableList<Long>,
    var sizeOfTrackIdsList: Int
):Parcelable
