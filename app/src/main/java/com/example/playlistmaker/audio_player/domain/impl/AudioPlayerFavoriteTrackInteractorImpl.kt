package com.example.playlistmaker.audio_player.domain.impl

import com.example.playlistmaker.main.domain.models.TrackDtoApp
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerFavoriteTrackInteractor
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerFavoriteTrackRepository
import com.example.playlistmaker.mediateka.data.TrackDbConvertor
import com.example.playlistmaker.mediateka.data.db.TrackEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AudioPlayerFavoriteTrackInteractorImpl(
    private val audioPlayerFavoriteTrackRepository: AudioPlayerFavoriteTrackRepository,
    private val trackDbConvertor: TrackDbConvertor
) : AudioPlayerFavoriteTrackInteractor {


    override suspend fun insertOneTrack(track: TrackDtoApp) {
        audioPlayerFavoriteTrackRepository.insertOneTrack(trackDbConvertor.map(track))
    }

    override suspend fun deleteOneTrack(trackId: TrackDtoApp) {
        audioPlayerFavoriteTrackRepository.deleteOneTrack(trackDbConvertor.map(trackId))
    }

    override suspend fun getTracksIds(): List<String> {
        return audioPlayerFavoriteTrackRepository.getTracksIds()
    }

    override suspend fun getTracks(): Flow<List<TrackDtoApp>> {
        return audioPlayerFavoriteTrackRepository.getTracks().map { track-> convertFromTrackEntity(track) }
    }

    private fun convertFromTrackEntity(tracks:List<TrackEntity>):List<TrackDtoApp> {
        return tracks.map { track -> trackDbConvertor.map(track) }
    }
}