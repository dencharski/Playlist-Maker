package com.example.playlistmaker.SearchActivity.data

import com.example.playlistmaker.SearchActivity.domain.api.SearchRepositoryInterface
import com.example.playlistmaker.SearchActivity.data.network.ITunesSearchInterface
import com.example.playlistmaker.SearchActivity.data.dto.ResponseModel
import retrofit2.Call
import retrofit2.Response

class SearchRepositoryImpl(private val iTunesSearchClient: ITunesSearchInterface?) : SearchRepositoryInterface {
    override fun searchTrack(text: String): Call<ResponseModel>? {
        return iTunesSearchClient?.search(text)
    }
}