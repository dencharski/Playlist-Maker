package com.example.playlistmaker.audio_player.data


import android.util.Log
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerSelectedTrackRepository
import com.example.playlistmaker.create_playlist.data.TrackInPlayListConvertor
import com.example.playlistmaker.main.domain.models.TrackApp
import com.example.playlistmaker.mediateka.data.TrackDbConvertor
import com.example.playlistmaker.mediateka.data.db.TrackEntity
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class AudioPlayerSelectedTrackRepositoryImpl(
    private val tracksDatabase: TracksDatabase,
    private val trackDbConvertor: TrackDbConvertor
) : AudioPlayerSelectedTrackRepository {

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

    override suspend fun getTracksIds(): List<String> {
       return withContext(Dispatchers.IO){
            tracksDatabase.trackDao().getTracksIds()
        }
    }

    override suspend fun getTracks():Flow<List<TrackApp>> = flow {
        emit( withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().getTracks()
                .map { track-> convertFromTrackEntity(track) }
        })
    }

    private fun convertFromTrackEntity(track:TrackEntity):TrackApp {
        return trackDbConvertor.map(track)
    }

}