package com.example.playlistmaker.SearchActivity

import android.content.SharedPreferences
import android.util.Log
import com.example.playlistmaker.Track
import com.google.gson.Gson

class SearchHistory(private val sharedPreferences: SharedPreferences) {

    private val trackList = arrayListOf<Track>()
    private val trackListId = arrayListOf<Long>()

    companion object {
        const val USER_LIST_KEY = "USER_LIST_KEY"
        const val capacity: Int = 10
        const val teg = "SearchActivity"
    }

    init {

        trackList.addAll(read())
        addListId()
        Log.d(teg, "init ${trackListId.size}")
    }

    fun writeOneTrack(track: Track) {

        val fTrack = arrayListOf<Track>()
        if (trackListId.contains(track.trackId)) {
            fTrack.add(track)
            trackList.forEach {
                if (it.trackId != track.trackId) {
                    fTrack.add(it)
                }
            }

        } else {
            fTrack.add(track)
            fTrack.addAll(trackList)
        }
        val ar = fTrack.take(capacity)
        fTrack.clear()
        fTrack.addAll(ar)
        Log.d(teg, "fTrack ${fTrack.size}")

        writeArray(fTrack)

        trackList.clear()
        trackList.addAll(read())
        addListId()

    }

    fun getTrackList() = trackList


    private fun addListId() {
        trackListId.clear()
        trackList.forEach { trackListId.add(it.trackId) }
    }

    private fun writeArray(tracks: ArrayList<Track>) {
        removeTrackListInSharedPreferences()
        val json = Gson().toJson(tracks)
        sharedPreferences.edit()
            .putString(USER_LIST_KEY, json)
            .apply()
        Log.d(teg, "Записали значение ${tracks.size}")
    }

    private fun read(): Array<Track> {
        val json = sharedPreferences.getString(USER_LIST_KEY, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<Track>::class.java)

    }

    fun removeTrackListInSharedPreferences() {
        sharedPreferences.edit()
            .remove(USER_LIST_KEY)
            .apply()
        trackList.clear()
        trackListId.clear()
        Log.d(teg, "remove sharedPreferences.size = ${read().size}")
    }
}