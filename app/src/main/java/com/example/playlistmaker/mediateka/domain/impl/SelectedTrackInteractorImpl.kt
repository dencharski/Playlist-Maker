package com.example.playlistmaker.mediateka.domain.impl

import android.util.Log
import com.example.playlistmaker.main.domain.models.TrackApp
import com.example.playlistmaker.mediateka.data.TrackDbConvertor
import com.example.playlistmaker.mediateka.data.db.TrackEntity
import com.example.playlistmaker.mediateka.domain.api.SelectedTrackInteractor
import com.example.playlistmaker.mediateka.domain.api.SelectedTracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SelectedTrackInteractorImpl(
    private val selectedTrackRepository: SelectedTracksRepository
) : SelectedTrackInteractor {
    override suspend fun insertOneTrack(track: TrackApp) {

        selectedTrackRepository.insertOneTrack(track)
    }

    override suspend fun deleteOneTrack(trackId: TrackApp) {
        selectedTrackRepository.deleteOneTrack(trackId)
    }

    override suspend fun getTracks(): Flow<List<TrackApp>> {
        return selectedTrackRepository.getTracks()
    }


}