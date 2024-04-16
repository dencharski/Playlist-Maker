package com.example.playlistmaker.audio_player.domain.impl

import android.util.Log
import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.main.domain.models.TrackApp
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerAddTrackInPlayListRepository
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerPlayListInteractor
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerPlayListRepository
import kotlinx.coroutines.flow.Flow

class AudioPlayerPlayListInteractorImpl(
    private val audioPlayerPlayListRepository: AudioPlayerPlayListRepository,
    private val audioPlayerAddTrackInPlayListRepository: AudioPlayerAddTrackInPlayListRepository


) : AudioPlayerPlayListInteractor {
    override suspend fun getListOfPlayLists(): Flow<List<PlayList>> {
        return audioPlayerPlayListRepository.getListOfPlayLists()
    }

    override suspend fun insertTrackIdInPlayList(playList: PlayList, trackId:Long) {
        audioPlayerPlayListRepository.insertTrackIdInPlayList(playList,trackId)
    }

    override suspend fun addTrackInPlayListTable(trackApp: TrackApp) {
        audioPlayerAddTrackInPlayListRepository.addTrackInPlayListTable(trackApp)
    }

    override fun checkTrackIdInPlayList(playList: PlayList, trackId: Long): Boolean {
        return audioPlayerPlayListRepository.checkTrackIdInPlayList(playList, trackId)
    }

}