package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.main.domain.models.TrackDtoApp
import kotlinx.coroutines.flow.Flow

interface SearchHistoryInteractor {
    fun writeOneTrack(track: TrackDtoApp)
    fun getTrackList(): Flow<List<TrackDtoApp>>
    fun removeTrackListInSharedPreferences()
}