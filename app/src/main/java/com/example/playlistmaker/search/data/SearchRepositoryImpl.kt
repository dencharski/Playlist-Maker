package com.example.playlistmaker.search.data

import android.util.Log
import com.bumptech.glide.load.engine.Resource
import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.mediateka.data.db.TrackEntity
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
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

class SearchRepositoryImpl(
    private val iTunesSearchClient: ITunesSearchInterface?,
    private val tracksDatabase: TracksDatabase
) :
    SearchRepository {
    override fun searchTrack(text: String): Flow<ResponseModel?> = flow {
        emit(withContext(Dispatchers.IO) {
            val favoriteTracks = tracksDatabase.trackDao().getTracksIds()
            try {
                val result = iTunesSearchClient?.search(text)

                if (result?.isSuccessful == true) {

                    result.body()?.let { map(it, favoriteTracks) }

                } else {
                    null
                }

            } catch (e: Throwable) {
                null
            }
        })
    }

    private fun map(responseModel: ResponseModel, listId: List<String>): ResponseModel {

        responseModel.results.forEach { result ->
            listId.forEach { id ->
                if (result.trackId.toString() == id) {
                    result.isFavorite = true
                }
            }
        }
        return responseModel
    }

}