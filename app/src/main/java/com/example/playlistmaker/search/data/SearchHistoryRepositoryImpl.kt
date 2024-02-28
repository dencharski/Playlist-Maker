package com.example.playlistmaker.search.data

import android.content.SharedPreferences
import android.util.Log
import com.example.playlistmaker.search.domain.api.SearchHistoryRepository
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import com.example.playlistmaker.search.data.dto.TrackHistoryDto
import com.example.playlistmaker.search.domain.models.ResponseModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class SearchHistoryRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val tracksDatabase: TracksDatabase
) :
    SearchHistoryRepository {

    private var trackList = arrayListOf<TrackHistoryDto>()
    private var trackListId = arrayListOf<Long>()

    init {
        trackList.addAll(read())
        addListId()
    }

    override fun writeOneTrack(track: TrackHistoryDto) {
        val arrayListOfTrackDtoApp = arrayListOf<TrackHistoryDto>()
        if (trackListId.contains(track.trackId)) {
            arrayListOfTrackDtoApp.add(track)
            trackList.forEach {
                if (it.trackId != track.trackId) {
                    arrayListOfTrackDtoApp.add(it)
                }
            }
        } else {
            arrayListOfTrackDtoApp.add(track)
            arrayListOfTrackDtoApp.addAll(trackList)
        }
        val ar = arrayListOfTrackDtoApp.take(capacity)
        arrayListOfTrackDtoApp.clear()
        arrayListOfTrackDtoApp.addAll(ar)
        Log.d(tag, "fTrack ${arrayListOfTrackDtoApp.size}")

        writeArray(arrayListOfTrackDtoApp)

        trackList.clear()
        trackList.addAll(read())

        addListId()
    }


    override fun getTrackList(): Flow<List<TrackHistoryDto>> = flow {
        emit(withContext(Dispatchers.IO) {
            val listId = tracksDatabase.trackDao().getTracksIds()
            trackList.clear()
            trackList.addAll(read())
            trackList.forEach { it.isFavorite = false }
            map(trackList, listId)

        })
    }

    override fun removeTrackListInSharedPreferences() {
        sharedPreferences.edit()
            .remove(USER_LIST_KEY)
            .apply()
        trackList.clear()
        trackListId.clear()

    }


    private fun map(
        arrayList: ArrayList<TrackHistoryDto>,
        listId: List<String>
    ): List<TrackHistoryDto> {

        arrayList.forEach { result ->
            listId.forEach { id ->
                if (result.trackId.toString() == id) {
                    result.isFavorite = true
                }
            }
        }


        return arrayList
    }

    private fun addListId() {
        trackListId.clear()
        trackList.forEach { trackListId.add(it.trackId) }
    }

    private fun writeArray(tracks: List<TrackHistoryDto>) {
        removeTrackListInSharedPreferences()
        val json = Gson().toJson(tracks)
        sharedPreferences.edit()
            .putString(USER_LIST_KEY, json)
            .apply()

    }

    private fun read(): Array<TrackHistoryDto> {
        val json = sharedPreferences.getString(USER_LIST_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<TrackHistoryDto>::class.java)
    }

    companion object {
        private const val USER_LIST_KEY = "USER_LIST_KEY"
        private const val capacity: Int = 10
        private const val tag = "SearchActivity"
    }
}