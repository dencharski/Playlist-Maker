package com.example.playlistmaker.search_activity.data

import com.example.playlistmaker.search_activity.domain.api.SearchRepository
import com.example.playlistmaker.search_activity.data.network.ITunesSearchInterface
import com.example.playlistmaker.search_activity.data.dto.ResponseModel
import retrofit2.Call

class SearchRepositoryImpl(private val iTunesSearchClient: ITunesSearchInterface?) : SearchRepository {
    override fun searchTrack(text: String): Call<ResponseModel>? {
        return iTunesSearchClient?.search(text)
    }
}