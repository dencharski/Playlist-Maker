package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.main.domain.models.TrackApp
import kotlinx.coroutines.flow.Flow

interface SearchHistoryInteractor {
    fun writeOneTrack(track: TrackApp)
    fun getTrackList(): Flow<List<TrackApp>>
    fun removeTrackListInSharedPreferences()
}