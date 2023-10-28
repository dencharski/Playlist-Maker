package com.example.playlistmaker.search_activity.domain.api

import com.example.playlistmaker.search_activity.domain.models.ResponseModel
import retrofit2.Call

interface SearchInteractor {
    fun searchTrack(text:String): Call<ResponseModel>?
}