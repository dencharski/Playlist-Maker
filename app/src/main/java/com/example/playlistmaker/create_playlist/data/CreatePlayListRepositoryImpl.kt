package com.example.playlistmaker.create_playlist.data

import android.util.Log
import com.example.playlistmaker.create_playlist.domain.api.CreatePlayListRepository
import com.example.playlistmaker.mediateka.data.db.PlayListEntity
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreatePlayListRepositoryImpl(private val tracksDatabase: TracksDatabase) :
    CreatePlayListRepository {
    override suspend fun createPlaylist(playList: PlayListEntity) {

        withContext(Dispatchers.IO) {
            tracksDatabase.playListDao().insertOnePlayList(playList)
        }

    }
}