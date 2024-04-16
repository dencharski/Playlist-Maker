package com.example.playlistmaker.audio_player.domain.api

import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.main.domain.models.TrackApp
import kotlinx.coroutines.flow.Flow

interface AudioPlayerPlayListInteractor {
    suspend fun getListOfPlayLists():Flow<List<PlayList>>
    suspend fun insertTrackIdInPlayList(playList: PlayList, trackId:Long)
    suspend fun addTrackInPlayListTable(trackApp: TrackApp)

    fun checkTrackIdInPlayList(playList: PlayList, trackId:Long):Boolean
}