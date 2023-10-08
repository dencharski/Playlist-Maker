package com.example.playlistmaker.SearchActivity.data.dto

import com.example.playlistmaker.TrackDtoApp

data class ResponseModel(
    val resultCount: String,
    val results: ArrayList <TrackDtoApp>
) {


}