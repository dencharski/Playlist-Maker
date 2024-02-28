package com.example.playlistmaker.mediateka.domain.impl

import com.example.playlistmaker.main.domain.models.TrackDtoApp
import com.example.playlistmaker.mediateka.data.TrackDbConvertor
import com.example.playlistmaker.mediateka.data.db.TrackEntity
import com.example.playlistmaker.mediateka.domain.api.SelectedTrackInteractor
import com.example.playlistmaker.mediateka.domain.api.SelectedTracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SelectedTrackInteractorImpl(
    private val selectedTrackRepository: SelectedTracksRepository,
    private val trackDbConvertor: TrackDbConvertor
) : SelectedTrackInteractor {
    override suspend fun insertOneTrack(track: TrackDtoApp) {
        selectedTrackRepository.insertOneTrack(trackDbConvertor.map(track))
    }

    override suspend fun deleteOneTrack(trackId: TrackDtoApp) {
        selectedTrackRepository.deleteOneTrack(trackDbConvertor.map(trackId))
    }

    override suspend fun getTracks(): Flow<List<TrackDtoApp>> {
        return selectedTrackRepository.getTracks().map { track ->convertFromTrackEntity(track) }
    }

    private fun convertFromTrackEntity(tracks: List<TrackEntity>): List<TrackDtoApp> {
        return tracks.map { track -> trackDbConvertor.map(track) }
    }
}