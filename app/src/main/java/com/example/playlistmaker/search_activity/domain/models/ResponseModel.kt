package com.example.playlistmaker.search_activity.domain.models

import com.example.playlistmaker.TrackDtoApp

data class ResponseModel(
    val resultCount: String,
    val results: ArrayList <TrackDtoApp>
) {


}