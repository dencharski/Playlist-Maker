package com.example.playlistmaker.search.domain.models

import com.example.playlistmaker.main.domain.models.TrackApp

data class ResponseModel(
    val resultCount: String,
    val results: ArrayList <TrackApp>
) {


}