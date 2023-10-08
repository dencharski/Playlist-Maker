package com.example.playlistmaker.SearchActivity.domain.api

import com.example.playlistmaker.TrackDtoApp

interface SearchHistoryInteractorInterface {
    fun writeOneTrack(track: TrackDtoApp)
    fun getTrackList(): ArrayList<TrackDtoApp>
    fun removeTrackListInSharedPreferences()
}