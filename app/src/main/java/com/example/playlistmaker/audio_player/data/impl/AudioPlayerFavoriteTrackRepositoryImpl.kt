package com.example.playlistmaker.audio_player.data.impl

import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerFavoriteTrackRepository
import com.example.playlistmaker.mediateka.data.TrackDbConvertor
import com.example.playlistmaker.mediateka.data.db.TrackEntity
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class AudioPlayerFavoriteTrackRepositoryImpl(
    private val tracksDatabase: TracksDatabase,
    private val trackDbConvertor: TrackDbConvertor
) : AudioPlayerFavoriteTrackRepository {

    override suspend fun insertOneTrack(track: TrackDtoApp) {
        withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().insertOneTrack(trackDbConvertor.map(track))
        }
    }

    override suspend fun deleteOneTrack(trackId: TrackDtoApp) {
        withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().deleteOneTrack(trackDbConvertor.map(trackId))
        }
    }

    override suspend fun getTracksIds(): List<String> {
       return withContext(Dispatchers.IO){
            tracksDatabase.trackDao().getTracksIds()
        }
    }

    override suspend fun getTracks() = flow {
        emit(withContext(Dispatchers.IO) {
            val movies = tracksDatabase.trackDao().getTracks()
            convertFromTrackEntity(movies)
        })
    }

    private fun convertFromTrackEntity(tracks: List<TrackEntity>): List<TrackDtoApp> {
        return tracks.map { track -> trackDbConvertor.map(track) }
    }


}