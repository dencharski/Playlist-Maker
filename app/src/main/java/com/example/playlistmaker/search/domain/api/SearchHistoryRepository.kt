package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.search.data.dto.TrackHistoryDto
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun writeOneTrack(track: TrackHistoryDto)
    fun getTrackList(): Flow<List<TrackHistoryDto>>
    fun removeTrackListInSharedPreferences()
}