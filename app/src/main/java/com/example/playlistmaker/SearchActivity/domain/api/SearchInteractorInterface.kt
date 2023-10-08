package com.example.playlistmaker.SearchActivity.domain.api

import com.example.playlistmaker.SearchActivity.data.dto.ResponseModel
import retrofit2.Call
import retrofit2.Response

interface SearchInteractorInterface {
    fun searchTrack(text:String): Call<ResponseModel>?
}