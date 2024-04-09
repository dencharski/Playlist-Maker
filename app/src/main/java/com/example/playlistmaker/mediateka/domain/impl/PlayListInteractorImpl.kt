package com.example.playlistmaker.mediateka.domain.impl

import com.example.playlistmaker.create_playlist.data.CreatePlayListDbConvertor
import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.main.domain.models.TrackDtoApp
import com.example.playlistmaker.mediateka.data.TrackDbConvertor
import com.example.playlistmaker.mediateka.data.db.PlayListEntity
import com.example.playlistmaker.mediateka.domain.api.AddTrackInPlayListRepository
import com.example.playlistmaker.mediateka.domain.api.PlayListInteractor
import com.example.playlistmaker.mediateka.domain.api.PlayListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayListInteractorImpl(
    private val playListRepository: PlayListRepository,
    private val createPlayListDbConvertor: CreatePlayListDbConvertor,
    private val addTrackInPlayListRepository: AddTrackInPlayListRepository,
    private val trackDbConvertor: TrackDbConvertor

) : PlayListInteractor {
    override suspend fun getListOfPlayLists(): Flow<List<PlayList>> {
        return playListRepository.getListOfPlayLists()
            .map { playlist -> convertFromPlayListEntity(playlist) }
    }

    override suspend fun insertTrackIdInPlaylist(playList: PlayList, trackId:Long) {
        playListRepository.createPlaylist(createPlayListDbConvertor.mapInsertChangePlayList(playList,trackId))
    }

    override suspend fun insertTrackDtoAppInPlayList(trackDtoApp: TrackDtoApp) {
        addTrackInPlayListRepository.addTrackInPlayList(trackDbConvertor.map(trackDtoApp))
    }

    private fun convertFromPlayListEntity(listOfPlaylists: List<PlayListEntity>): List<PlayList> {
        return listOfPlaylists.map { playlist -> createPlayListDbConvertor.map(playlist) }
    }
}