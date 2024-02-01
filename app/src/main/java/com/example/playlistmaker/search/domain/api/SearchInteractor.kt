package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.search.domain.models.ResponseModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

interface SearchInteractor {
     fun searchTrack(text:String): Flow<ResponseModel?>
}