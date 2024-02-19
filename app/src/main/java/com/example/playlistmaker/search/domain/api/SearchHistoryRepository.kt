package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.TrackDtoApp
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun writeOneTrack(track: TrackDtoApp)
    fun getTrackList(): Flow<ArrayList<TrackDtoApp>>
    fun removeTrackListInSharedPreferences()
}