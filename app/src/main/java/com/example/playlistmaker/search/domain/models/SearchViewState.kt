package com.example.playlistmaker.search.domain.models

import com.example.playlistmaker.main.domain.models.TrackDtoApp

sealed class SearchViewState {
    object Loading : SearchViewState()
    object Error : SearchViewState()
    object Empty : SearchViewState()
    data class SearchViewStateData(
        var trackList: ArrayList<TrackDtoApp> = arrayListOf<TrackDtoApp>(),
    ) : SearchViewState()

    data class SearchViewStateDataHistory(
        var trackListHistory: ArrayList<TrackDtoApp> = arrayListOf<TrackDtoApp>()
    ) : SearchViewState()

}
