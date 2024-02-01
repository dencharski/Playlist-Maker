package com.example.playlistmaker.search.data

import android.util.Log
import com.bumptech.glide.load.engine.Resource
import com.example.playlistmaker.search.domain.api.SearchRepository
import com.example.playlistmaker.search.data.network.ITunesSearchInterface
import com.example.playlistmaker.search.domain.models.ResponseModel
import com.example.playlistmaker.search.ui.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class SearchRepositoryImpl(private val iTunesSearchClient: ITunesSearchInterface?) :
    SearchRepository {
    override fun searchTrack(text: String): Flow<ResponseModel?> = flow {
        emit(withContext(Dispatchers.IO) {
            try {
                val result = iTunesSearchClient?.search(text)

                if (result?.isSuccessful == true)  result.body() else null

            } catch (e: Throwable) {
                null
            }
        })
    }
}