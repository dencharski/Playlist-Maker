package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.search.domain.api.SearchHistoryInteractor
import com.example.playlistmaker.search.domain.api.SearchHistoryRepository
import com.example.playlistmaker.main.domain.models.TrackApp
import com.example.playlistmaker.search.data.TrackHistoryConvertor
import com.example.playlistmaker.search.data.dto.TrackHistoryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchHistoryInteractorImpl(
    private val searchHistoryRepository: SearchHistoryRepository
) :
    SearchHistoryInteractor {
    override fun writeOneTrack(track: TrackApp) {
        searchHistoryRepository.writeOneTrack(track)
    }

    override fun getTrackList(): Flow<List<TrackApp>> {
        return searchHistoryRepository.getTrackList()
    }

    override fun removeTrackListInSharedPreferences() {
        searchHistoryRepository.removeTrackListInSharedPreferences()
    }


}