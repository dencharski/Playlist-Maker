package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.TrackDtoApp

interface SearchHistoryRepository {
    fun writeOneTrack(track: TrackDtoApp)
    fun getTrackList(): ArrayList<TrackDtoApp>
    fun removeTrackListInSharedPreferences()
}