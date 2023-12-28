package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.search.domain.models.ResponseModel
import retrofit2.Call

interface SearchRepository {
    fun searchTrack(text:String): Call<ResponseModel>?
}