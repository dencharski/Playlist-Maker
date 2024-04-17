package com.example.playlistmaker.audio_player.data

import android.util.Log
import com.example.playlistmaker.create_playlist.data.TrackInPlayListConvertor
import com.example.playlistmaker.main.domain.models.TrackApp
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerAddTrackInPlayListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AudioPlayerAddTrackInPlayListRepositoryImpl(
    private val tracksDatabase: TracksDatabase,
    private val trackInPlayListConvertor: TrackInPlayListConvertor
) :
    AudioPlayerAddTrackInPlayListRepository {
    override suspend fun addTrackInPlayListTable(track: TrackApp) {

        withContext(Dispatchers.IO) {
            tracksDatabase.trackInPlayListDao().addOneTrackInPlayList(trackInPlayListConvertor.map(track))
        }

    }
}
