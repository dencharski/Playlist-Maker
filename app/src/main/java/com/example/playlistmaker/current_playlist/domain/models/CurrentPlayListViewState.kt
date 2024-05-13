package com.example.playlistmaker.current_playlist.domain.models

import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.main.domain.models.TrackApp

sealed class CurrentPlayListViewState {
    data class EmptyPlayList(val error: String) : CurrentPlayListViewState()
    data class PlayListState(val playList: PlayList) : CurrentPlayListViewState()
    data class ListOfTracks(val listOfTracks: List<TrackApp>) : CurrentPlayListViewState()
    data class DeleteTrackIdFromPlayList(val completed: Unit) : CurrentPlayListViewState()

}