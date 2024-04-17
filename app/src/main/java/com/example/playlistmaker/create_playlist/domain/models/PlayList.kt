package com.example.playlistmaker.create_playlist.domain.models

data class PlayList(
    val playListId: String,
    var playListName: String,
    var playlistDescription: String,
    var playlistImageUri: String,
    var listOfTrackIds: MutableList<Long>,
    var sizeOfTrackIdsList: Int
)
