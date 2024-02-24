package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.search.domain.api.SearchRepository
import com.example.playlistmaker.search.domain.models.ResponseModel
import kotlinx.coroutines.flow.Flow

class SearchInteractorImpl(private val searchRepository: SearchRepository) :
    SearchInteractor {
    override suspend fun searchTrack(text: String): Flow<ResponseModel?> {
        return searchRepository.searchTrack(text)
    }

    override suspend fun getTrackIds(): List<String> {
        return searchRepository.getTrackIds()
    }

    override suspend fun refreshTrackDatabaseStatus(): Flow<ResponseModel?> {
        return searchRepository.refreshTrackDatabaseStatus()
    }
}