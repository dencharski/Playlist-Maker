package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.search.domain.models.ResponseModel
import retrofit2.Call

interface SearchInteractor {
    fun searchTrack(text:String): Call<ResponseModel>?
}