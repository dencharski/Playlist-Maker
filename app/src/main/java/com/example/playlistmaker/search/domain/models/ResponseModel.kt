package com.example.playlistmaker.search.domain.models

import com.example.playlistmaker.TrackDtoApp

data class ResponseModel(
    val resultCount: String,
    val results: ArrayList <TrackDtoApp>
) {


}