package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.search.domain.api.SearchRepository
import com.example.playlistmaker.search.domain.models.ResponseModel
import retrofit2.Call

class SearchInteractorImpl(private val searchRepository: SearchRepository) :
    SearchInteractor {
    override fun searchTrack(text: String): Call<ResponseModel>? {
        return searchRepository.searchTrack(text)
    }
}