package com.example.playlistmaker.mediateka.domain.api

import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.main.domain.models.TrackDtoApp
import kotlinx.coroutines.flow.Flow

interface PlayListInteractor {
    suspend fun getListOfPlayLists():Flow<List<PlayList>>
    suspend fun insertTrackIdInPlaylist(playList: PlayList, trackId:Long)
    suspend fun insertTrackDtoAppInPlayList(trackDtoApp: TrackDtoApp)
}