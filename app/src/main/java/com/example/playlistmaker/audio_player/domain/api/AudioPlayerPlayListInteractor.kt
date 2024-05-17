package com.example.playlistmaker.audio_player.domain.api

import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.main.domain.models.TrackApp
import kotlinx.coroutines.flow.Flow

interface AudioPlayerPlayListInteractor {
    suspend fun getListOfPlayLists():Flow<List<PlayList>>
    suspend fun insertTrackIdInPlayList(playList: PlayList, trackId:Long)
    suspend fun addTrackInPlayListTable(trackApp: TrackApp)
    suspend fun getPlayList(playListId:Long):PlayList

    suspend fun checkTrackIdInAllPlayListsTrackIds(trackId:Long):Boolean

    suspend fun deleteTrackIdInPlayList(playList: PlayList, trackId:Long)
    suspend fun deleteTrackInPlayListTable(trackId: Long)

    suspend fun deletePlayList(playList: PlayList)
    suspend fun getAllTracksInPlayList(listOfTrackId:List<Long>):Flow<List<TrackApp>>
    fun checkTrackIdInPlayList(playList: PlayList, trackId:Long):Boolean
}