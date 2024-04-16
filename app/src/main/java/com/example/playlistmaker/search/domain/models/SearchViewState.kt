package com.example.playlistmaker.search.domain.models

import com.example.playlistmaker.main.domain.models.TrackApp

sealed class SearchViewState {
    object Loading : SearchViewState()
    object Error : SearchViewState()
    object Empty : SearchViewState()
    data class SearchViewStateData(
        var trackList: ArrayList<TrackApp> = arrayListOf<TrackApp>(),
    ) : SearchViewState()

    data class SearchViewStateDataHistory(
        var trackListHistory: ArrayList<TrackApp> = arrayListOf<TrackApp>()
    ) : SearchViewState()

}
