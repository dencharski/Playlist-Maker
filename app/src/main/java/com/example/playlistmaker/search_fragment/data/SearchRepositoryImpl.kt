package com.example.playlistmaker.search_fragment.data

import com.example.playlistmaker.search_fragment.domain.api.SearchRepository
import com.example.playlistmaker.search_fragment.data.network.ITunesSearchInterface
import com.example.playlistmaker.search_fragment.domain.models.ResponseModel
import retrofit2.Call

class SearchRepositoryImpl(private val iTunesSearchClient: ITunesSearchInterface?) : SearchRepository {
    override fun searchTrack(text: String): Call<ResponseModel>? {
        return iTunesSearchClient?.search(text)
    }
}