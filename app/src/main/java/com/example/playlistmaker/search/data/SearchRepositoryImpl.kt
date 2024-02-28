package com.example.playlistmaker.search.data


import android.util.Log
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import com.example.playlistmaker.search.domain.api.SearchRepository
import com.example.playlistmaker.search.data.network.ITunesSearchInterface
import com.example.playlistmaker.search.domain.models.ResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response


class SearchRepositoryImpl(
    private val iTunesSearchClient: ITunesSearchInterface?,
    private val tracksDatabase: TracksDatabase
) :
    SearchRepository {
    private var responseResult: Response<ResponseModel>? = null
    override suspend fun searchTrack(text: String): Flow<ResponseModel?> = flow {
        emit(withContext(Dispatchers.IO) {
            val favoriteTracks = getTrackIds()
            try {
                val response = iTunesSearchClient?.search(text)

                if (response?.isSuccessful == true) {
                    responseResult = response
                    response.body()?.let { map(it, favoriteTracks) }

                } else {
                    null
                }

            } catch (e: Throwable) {
                null
            }
        })
    }

    override suspend fun getTrackIds(): List<String> {
        return (withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().getTracksIds()
        })
    }

    override suspend fun refreshTrackDatabaseStatus(): Flow<ResponseModel?> = flow {
        emit(withContext(Dispatchers.IO) {
            val favoriteTracks = getTrackIds()
            try {

                if (responseResult != null) {
                    responseResult?.body()?.results?.forEach { it.isFavorite = false }
                    responseResult?.body()?.let { map(it, favoriteTracks) }

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