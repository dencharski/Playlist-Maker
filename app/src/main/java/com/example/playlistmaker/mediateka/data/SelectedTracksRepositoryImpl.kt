package com.example.playlistmaker.mediateka.data

import android.util.Log
import com.example.playlistmaker.main.domain.models.TrackApp
import com.example.playlistmaker.mediateka.data.db.TrackEntity
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import com.example.playlistmaker.mediateka.domain.api.SelectedTracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SelectedTracksRepositoryImpl(
    private val tracksDatabase: TracksDatabase,
    private val trackDbConvertor: TrackDbConvertor
) : SelectedTracksRepository {
    override suspend fun insertOneTrack(track: TrackApp) {
        withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().insertOneTrack(trackDbConvertor.map(track))
        }
    }

    override suspend fun deleteOneTrack(trackId: TrackApp) {
        withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().deleteOneTrack(trackDbConvertor.map(trackId))
        }
    }

    override suspend fun getTracks() = flow {
        emit(withContext(Dispatchers.IO) {
             tracksDatabase.trackDao().getTracks().reversed().map { track ->convertFromTrackEntity(track) }
        })

    }

    private fun convertFromTrackEntity(track:TrackEntity): TrackApp {
        return  trackDbConvertor.map(track)
    }
}