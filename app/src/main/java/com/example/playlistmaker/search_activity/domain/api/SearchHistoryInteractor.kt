package com.example.playlistmaker.search_activity.domain.api

import com.example.playlistmaker.TrackDtoApp

interface SearchHistoryInteractor {
    fun writeOneTrack(track: TrackDtoApp)
    fun getTrackList(): ArrayList<TrackDtoApp>
    fun removeTrackListInSharedPreferences()
}