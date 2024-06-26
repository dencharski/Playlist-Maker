package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.main.domain.models.TrackApp
import com.example.playlistmaker.search.data.dto.TrackHistoryDto
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun writeOneTrack(track: TrackApp)
    fun getTrackList(): Flow<List<TrackApp>>
    fun removeTrackListInSharedPreferences()
}