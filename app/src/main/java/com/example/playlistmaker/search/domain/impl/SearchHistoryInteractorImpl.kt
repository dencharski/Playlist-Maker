package com.example.playlistmaker.search.domain.impl

import android.util.Log
import com.example.playlistmaker.search.domain.api.SearchHistoryInteractor
import com.example.playlistmaker.search.domain.api.SearchHistoryRepository
import com.example.playlistmaker.main.domain.models.TrackDtoApp
import com.example.playlistmaker.search.data.TrackHistoryConvertor
import com.example.playlistmaker.search.data.dto.TrackHistoryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchHistoryInteractorImpl(
    private val searchHistoryRepository: SearchHistoryRepository,
    private val trackHistoryConvertor: TrackHistoryConvertor
) :
    SearchHistoryInteractor {
    override fun writeOneTrack(track: TrackDtoApp) {
        searchHistoryRepository.writeOneTrack(trackHistoryConvertor.map(track))
    }

    override fun getTrackList(): Flow<List<TrackDtoApp>> {
        return searchHistoryRepository.getTrackList().map { track-> convertFromTrackHistory(track) }
    }

    override fun removeTrackListInSharedPreferences() {
        searchHistoryRepository.removeTrackListInSharedPreferences()
    }

    private fun convertFromTrackHistory(tracks:List<TrackHistoryDto>):List<TrackDtoApp> {
         return tracks.map { track -> trackHistoryConvertor.map(track) }
    }
}