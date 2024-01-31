package com.example.playlistmaker.search.data.network

import com.example.playlistmaker.search.domain.models.ResponseModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesSearchInterface {
    @GET("search?entity=song")
    suspend fun search(@Query("term") text: String):Response<ResponseModel>?
}