package com.example.playlistmaker.search_fragment.domain.impl

import com.example.playlistmaker.search_fragment.domain.api.SearchHistoryInteractor
import com.example.playlistmaker.search_fragment.domain.api.SearchHistoryRepository
import com.example.playlistmaker.TrackDtoApp

class SearchHistoryInteractorImpl(private val searchHistoryRepository: SearchHistoryRepository) :
    SearchHistoryInteractor {
    override fun writeOneTrack(track: TrackDtoApp) {
        searchHistoryRepository.writeOneTrack(track)
    }

    override fun getTrackList(): ArrayList<TrackDtoApp> {
        return searchHistoryRepository.getTrackList()
    }

    override fun removeTrackListInSharedPreferences() {
        searchHistoryRepository.removeTrackListInSharedPreferences()
    }
}