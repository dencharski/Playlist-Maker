package com.example.playlistmaker.search_activity.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ITunesSearchClient {

    companion object {
        private var instance: Retrofit? = null
        private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        fun getApiRetrofitClient(): Retrofit? {
            if (instance == null) {
                instance = Retrofit.Builder()
                    .baseUrl("https://itunes.apple.com/")
                    .client(
                        OkHttpClient.Builder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .addInterceptor(logging)
                            .build()
                    )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return instance
        }

    }
}