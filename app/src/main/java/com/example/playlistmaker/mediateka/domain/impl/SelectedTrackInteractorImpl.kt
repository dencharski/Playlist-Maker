package com.example.playlistmaker.mediateka.domain.impl

import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.mediateka.data.db.TrackEntity
import com.example.playlistmaker.mediateka.domain.api.SelectedTrackInteractor
import com.example.playlistmaker.mediateka.domain.api.SelectedTracksRepository
import kotlinx.coroutines.flow.Flow

class SelectedTrackInteractorImpl(private val selectedTrackRepository:SelectedTracksRepository):SelectedTrackInteractor {
    override suspend fun insertOneTrack(track: TrackEntity) {
        selectedTrackRepository.insertOneTrack(track)
    }

    override suspend fun deleteOneTrack(trackId: TrackDtoApp) {
        selectedTrackRepository.deleteOneTrack(trackId)
    }

    override suspend fun getTracks(): Flow<List<TrackDtoApp>> {
        return selectedTrackRepository.getTracks()
    }
}