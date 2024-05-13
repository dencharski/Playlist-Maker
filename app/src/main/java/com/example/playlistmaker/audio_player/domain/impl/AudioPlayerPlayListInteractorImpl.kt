package com.example.playlistmaker.audio_player.domain.impl

import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.main.domain.models.TrackApp
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerAllTrackInPlayListRepository
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerPlayListInteractor
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerPlayListRepository
import kotlinx.coroutines.flow.Flow

class AudioPlayerPlayListInteractorImpl(
    private val audioPlayerPlayListRepository: AudioPlayerPlayListRepository,
    private val audioPlayerAllTrackInPlayListRepository: AudioPlayerAllTrackInPlayListRepository


) : AudioPlayerPlayListInteractor {
    override suspend fun getListOfPlayLists(): Flow<List<PlayList>> {
        return audioPlayerPlayListRepository.getListOfPlayLists()
    }

    override fun checkTrackIdInPlayList(playList: PlayList, trackId: Long): Boolean {
        return audioPlayerPlayListRepository.checkTrackIdInPlayList(playList, trackId)
    }

    override suspend fun insertTrackIdInPlayList(playList: PlayList, trackId: Long) {
        audioPlayerPlayListRepository.insertTrackIdInPlayList(playList, trackId)
    }

    override suspend fun getPlayList(playListId: Long): PlayList {
        return audioPlayerPlayListRepository.getPlayList(playListId)
    }

    override suspend fun checkTrackIdInAllPlayListsTrackIds(trackId: Long): Boolean {
        return audioPlayerPlayListRepository.checkTrackIdInAllPlayListsTrackIds(trackId)
    }

    override suspend fun deleteTrackIdInPlayList(playList: PlayList, trackId: Long) {
         audioPlayerPlayListRepository.deleteTrackIdInPlayList(playList, trackId)
    }

    override suspend fun addTrackInPlayListTable(trackApp: TrackApp) {
        audioPlayerAllTrackInPlayListRepository.addTrackInPlayListTable(trackApp)
    }

    override suspend fun deleteTrackInPlayListTable(trackId: Long) {
        audioPlayerAllTrackInPlayListRepository.deleteOneTrackFromPlayList(trackId)
    }

    override suspend fun deletePlayList(playList: PlayList) {
        audioPlayerPlayListRepository.deletePlayList(playList)
    }

    override suspend fun getAllTracksInPlayList(listOfTrackId: List<Long>): Flow<List<TrackApp>> {
        return audioPlayerAllTrackInPlayListRepository.getAllTracksInPlayList(listOfTrackId)
    }


}