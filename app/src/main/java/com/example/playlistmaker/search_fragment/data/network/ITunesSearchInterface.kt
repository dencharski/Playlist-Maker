package com.example.playlistmaker.search_fragment.data.network

import com.example.playlistmaker.search_fragment.domain.models.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesSearchInterface {
    @GET("search?entity=song")
    fun search(@Query("term") text: String):Call<ResponseModel>
}