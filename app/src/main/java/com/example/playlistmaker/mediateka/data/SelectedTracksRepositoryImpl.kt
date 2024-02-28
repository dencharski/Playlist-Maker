package com.example.playlistmaker.mediateka.data

import com.example.playlistmaker.mediateka.data.db.TrackEntity
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import com.example.playlistmaker.mediateka.domain.api.SelectedTracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class SelectedTracksRepositoryImpl(
    private val tracksDatabase: TracksDatabase
) : SelectedTracksRepository {
    override suspend fun insertOneTrack(track: TrackEntity) {
        withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().insertOneTrack(track)
        }
    }

    override suspend fun deleteOneTrack(trackId: TrackEntity) {
        withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().deleteOneTrack(trackId)
        }
    }

    override suspend fun getTracks() = flow {
        emit(withContext(Dispatchers.IO) {
             tracksDatabase.trackDao().getTracks().reversed()
        })

    }
}