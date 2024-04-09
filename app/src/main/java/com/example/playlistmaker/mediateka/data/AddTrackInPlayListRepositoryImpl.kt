package com.example.playlistmaker.mediateka.data

import com.example.playlistmaker.mediateka.data.db.TrackEntity
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import com.example.playlistmaker.mediateka.domain.api.AddTrackInPlayListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddTrackInPlayListRepositoryImpl(private val tracksDatabase: TracksDatabase) :
    AddTrackInPlayListRepository {
    override suspend fun addTrackInPlayList(track: TrackEntity) {
        withContext(Dispatchers.IO) {
            tracksDatabase.trackInPlayListDao().insertOneTrack(track)
        }

    }
}