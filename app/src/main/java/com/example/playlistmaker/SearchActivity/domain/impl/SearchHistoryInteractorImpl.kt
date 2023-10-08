package com.example.playlistmaker.SearchActivity.domain.impl

import com.example.playlistmaker.SearchActivity.domain.api.SearchHistoryInteractorInterface
import com.example.playlistmaker.SearchActivity.domain.api.SearchHistoryRepositoryInterface
import com.example.playlistmaker.TrackDtoApp

class SearchHistoryInteractorImpl(private val searchHistoryRepository: SearchHistoryRepositoryInterface) :
    SearchHistoryInteractorInterface {
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