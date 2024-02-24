package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.search.domain.models.ResponseModel
import kotlinx.coroutines.flow.Flow

interface SearchInteractor {
    suspend fun searchTrack(text:String): Flow<ResponseModel?>
    suspend fun getTrackIds():List<String>
    suspend fun refreshTrackDatabaseStatus():Flow<ResponseModel?>
}