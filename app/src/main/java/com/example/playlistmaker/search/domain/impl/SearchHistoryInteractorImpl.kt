package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.search.domain.api.SearchHistoryInteractor
import com.example.playlistmaker.search.domain.api.SearchHistoryRepository
import com.example.playlistmaker.TrackDtoApp
import kotlinx.coroutines.flow.Flow

class SearchHistoryInteractorImpl(private val searchHistoryRepository: SearchHistoryRepository) :
    SearchHistoryInteractor {
    override fun writeOneTrack(track: TrackDtoApp) {
        searchHistoryRepository.writeOneTrack(track)
    }

    override fun getTrackList(): Flow<ArrayList<TrackDtoApp>> {
        return searchHistoryRepository.getTrackList()
    }

    override fun removeTrackListInSharedPreferences() {
        searchHistoryRepository.removeTrackListInSharedPreferences()
    }
}