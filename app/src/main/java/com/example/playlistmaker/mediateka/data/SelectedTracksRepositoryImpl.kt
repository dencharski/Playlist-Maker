package com.example.playlistmaker.mediateka.data

import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.mediateka.data.db.TrackEntity
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import com.example.playlistmaker.mediateka.domain.api.SelectedTracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class SelectedTracksRepositoryImpl(
    private val tracksDatabase: TracksDatabase,
    private val trackDbConvertor: TrackDbConvertor
) : SelectedTracksRepository {
    override suspend fun insertOneTrack(track: TrackEntity) {
        withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().insertOneTrack(track)
        }
    }

    override suspend fun deleteOneTrack(trackId: TrackDtoApp) {
        withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().deleteOneTrack(trackDbConvertor.map(trackId))
        }
    }

    override suspend fun getTracks() = flow {
        emit(withContext(Dispatchers.IO) {
            val movies = tracksDatabase.trackDao().getTracks()
            convertFromTrackEntity(movies.reversed())
        })

    }

    private fun convertFromTrackEntity(tracks: List<TrackEntity>): List<TrackDtoApp> {
        return tracks.map { track -> trackDbConvertor.map(track) }
    }
}