package com.example.playlistmaker.search_activity.data.dto

import com.example.playlistmaker.TrackDtoApp

data class ResponseModel(
    val resultCount: String,
    val results: ArrayList <TrackDtoApp>
) {


}