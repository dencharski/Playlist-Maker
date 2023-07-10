package com.example.playlistmaker.internet

import com.example.playlistmaker.Track

data class ResponseModel(
    val resultCount: String,
    val results: ArrayList <Track>
) {


}