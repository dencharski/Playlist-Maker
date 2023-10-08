package com.example.playlistmaker.SearchActivity.domain.impl

import com.example.playlistmaker.SearchActivity.domain.api.SearchInteractorInterface
import com.example.playlistmaker.SearchActivity.domain.api.SearchRepositoryInterface
import com.example.playlistmaker.SearchActivity.data.dto.ResponseModel
import retrofit2.Call
import retrofit2.Response

class SearchInteractorImpl(private val searchRepository: SearchRepositoryInterface) :
    SearchInteractorInterface {
    override fun searchTrack(text: String): Call<ResponseModel>? {
        return searchRepository.searchTrack(text)
    }
}