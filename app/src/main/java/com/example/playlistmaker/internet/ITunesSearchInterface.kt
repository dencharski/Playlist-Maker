package com.example.playlistmaker.internet

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesSearchInterface {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String):Call<ResponseModel>
}