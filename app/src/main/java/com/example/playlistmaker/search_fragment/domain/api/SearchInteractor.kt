package com.example.playlistmaker.search_fragment.domain.api

import com.example.playlistmaker.search_fragment.domain.models.ResponseModel
import retrofit2.Call

interface SearchInteractor {
    fun searchTrack(text:String): Call<ResponseModel>?
}