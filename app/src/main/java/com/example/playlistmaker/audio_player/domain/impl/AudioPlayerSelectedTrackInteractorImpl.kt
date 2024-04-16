package com.example.playlistmaker.audio_player.domain.impl

import android.util.Log
import com.example.playlistmaker.main.domain.models.TrackApp
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerSelectedTrackInteractor
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerSelectedTrackRepository
import kotlinx.coroutines.flow.Flow

class AudioPlayerSelectedTrackInteractorImpl(
    private val audioPlayerSelectedTrackRepository: AudioPlayerSelectedTrackRepository
) : AudioPlayerSelectedTrackInteractor {


    override suspend fun insertOneTrack(track: TrackApp) {
        audioPlayerSelectedTrackRepository.insertOneTrack(track)
    }

    override suspend fun deleteOneTrack(trackId: TrackApp) {
        audioPlayerSelectedTrackRepository.deleteOneTrack(trackId)
    }

    override suspend fun getTracksIds(): List<String> {
        return audioPlayerSelectedTrackRepository.getTracksIds()
    }

    override suspend fun getTracks(): Flow<List<TrackApp>> {
        return audioPlayerSelectedTrackRepository.getTracks()
    }


}