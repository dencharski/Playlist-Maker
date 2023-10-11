package com.example.playlistmaker.search_activity.domain.impl

import com.example.playlistmaker.search_activity.domain.api.SearchInteractor
import com.example.playlistmaker.search_activity.domain.api.SearchRepository
import com.example.playlistmaker.search_activity.data.dto.ResponseModel
import retrofit2.Call

class SearchInteractorImpl(private val searchRepository: SearchRepository) :
    SearchInteractor {
    override fun searchTrack(text: String): Call<ResponseModel>? {
        return searchRepository.searchTrack(text)
    }
}