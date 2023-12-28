package com.example.playlistmaker.search.data

import android.content.SharedPreferences
import android.util.Log
import com.example.playlistmaker.search.domain.api.SearchHistoryRepository
import com.example.playlistmaker.TrackDtoApp
import com.google.gson.Gson

class SearchHistoryRepositoryImpl(private val sharedPreferences: SharedPreferences) :
    SearchHistoryRepository {

    private val trackList = arrayListOf<TrackDtoApp>()
    private val trackListId = arrayListOf<Long>()

    init {
        trackList.addAll(read())
        addListId()
        Log.d(teg, "init ${trackListId.size}")
    }

    override fun writeOneTrack(track: TrackDtoApp) {
        val arrayListOfTrackDtoApp = arrayListOf<TrackDtoApp>()
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
        Log.d(teg, "fTrack ${arrayListOfTrackDtoApp.size}")

        writeArray(arrayListOfTrackDtoApp)

        trackList.clear()
        trackList.addAll(read())
        addListId()
    }

    override fun getTrackList() = trackList
    override fun removeTrackListInSharedPreferences() {
        sharedPreferences.edit()
            .remove(USER_LIST_KEY)
            .apply()
        trackList.clear()
        trackListId.clear()
        Log.d(teg, "remove sharedPreferences.size = ${read().size}")
    }

    private fun addListId() {
        trackListId.clear()
        trackList.forEach { trackListId.add(it.trackId) }
    }

    private fun writeArray(tracks: ArrayList<TrackDtoApp>) {
        removeTrackListInSharedPreferences()
        val json = Gson().toJson(tracks)
        sharedPreferences.edit()
            .putString(USER_LIST_KEY, json)
            .apply()
        Log.d(teg, "write track ${tracks.size}")
    }

    private fun read(): Array<TrackDtoApp> {
        val json = sharedPreferences.getString(USER_LIST_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<TrackDtoApp>::class.java)
    }

    companion object {
        private const val USER_LIST_KEY = "USER_LIST_KEY"
        private const val capacity: Int = 10
        private const val teg = "SearchActivity"
    }
}