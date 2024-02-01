package com.example.playlistmaker.search.domain.api

import com.bumptech.glide.load.engine.Resource
import com.example.playlistmaker.search.domain.models.ResponseModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

interface SearchRepository {
     fun searchTrack(text:String): Flow<ResponseModel?>
}