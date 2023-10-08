package com.example.playlistmaker.SearchActivity.domain.api

import com.example.playlistmaker.TrackDtoApp

interface SearchHistoryRepositoryInterface {
    fun writeOneTrack(track: TrackDtoApp)
    fun getTrackList(): ArrayList<TrackDtoApp>
    fun removeTrackListInSharedPreferences()
}