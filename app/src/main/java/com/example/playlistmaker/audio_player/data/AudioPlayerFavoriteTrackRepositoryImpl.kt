package com.example.playlistmaker.audio_player.data


import com.example.playlistmaker.audio_player.domain.api.AudioPlayerFavoriteTrackRepository
import com.example.playlistmaker.mediateka.data.db.TrackEntity
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class AudioPlayerFavoriteTrackRepositoryImpl(
    private val tracksDatabase: TracksDatabase
) : AudioPlayerFavoriteTrackRepository {

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

    override suspend fun getTracksIds(): List<String> {
       return withContext(Dispatchers.IO){
            tracksDatabase.trackDao().getTracksIds()
        }
    }

    override suspend fun getTracks() = flow {
        emit(withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().getTracks()
        })
    }

}