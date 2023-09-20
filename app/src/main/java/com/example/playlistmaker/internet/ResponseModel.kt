package com.example.playlistmaker.internet

import com.example.playlistmaker.TrackDtoApp

data class ResponseModel(
    val resultCount: String,
    val results: ArrayList <TrackDtoApp>
) {


}