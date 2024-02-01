package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.search.domain.api.SearchRepository
import com.example.playlistmaker.search.domain.models.ResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class SearchInteractorImpl(private val searchRepository: SearchRepository) :
    SearchInteractor {
    override fun searchTrack(text: String): Flow<ResponseModel?> {
        return searchRepository.searchTrack(text)
    }
}