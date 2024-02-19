package com.example.playlistmaker.audio_player.domain.impl

import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerFavoriteTrackInteractor
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerFavoriteTrackRepository
import kotlinx.coroutines.flow.Flow

class AudioPlayerFavoriteTrackInteractorImpl(private val audioPlayerFavoriteTrackRepository: AudioPlayerFavoriteTrackRepository) :
    AudioPlayerFavoriteTrackInteractor {


    override suspend fun insertOneTrack(track: TrackDtoApp) {
        audioPlayerFavoriteTrackRepository.insertOneTrack(track)
    }

    override suspend fun deleteOneTrack(trackId: TrackDtoApp) {
        audioPlayerFavoriteTrackRepository.deleteOneTrack(trackId)
    }

    override suspend fun getTracksIds(): List<String> {
        return audioPlayerFavoriteTrackRepository.getTracksIds()
    }

    override suspend fun getTracks(): Flow<List<TrackDtoApp>> {
        return audioPlayerFavoriteTrackRepository.getTracks()
    }
}