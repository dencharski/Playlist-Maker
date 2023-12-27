package com.example.playlistmaker.search_fragment.domain.api

import com.example.playlistmaker.search_fragment.domain.models.ResponseModel
import retrofit2.Call

interface SearchRepository {
    fun searchTrack(text:String): Call<ResponseModel>?
}