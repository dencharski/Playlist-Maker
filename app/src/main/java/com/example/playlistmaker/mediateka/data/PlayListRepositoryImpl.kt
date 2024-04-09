package com.example.playlistmaker.mediateka.data

import android.util.Log
import com.example.playlistmaker.mediateka.data.db.PlayListEntity
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import com.example.playlistmaker.mediateka.domain.api.PlayListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class PlayListRepositoryImpl(private val tracksDatabase: TracksDatabase) : PlayListRepository {
    override suspend fun getListOfPlayLists()= flow {
            emit(withContext(Dispatchers.IO) {
                tracksDatabase.playListDao().getPlayLists()
            })
        }

    override suspend fun insertPlaylist(playList: PlayListEntity) {
        withContext(Dispatchers.IO){
            tracksDatabase.playListDao().insertOnePlayList(playList)
        }
    }

    override suspend fun createPlaylist(playList: PlayListEntity) {

        withContext(Dispatchers.IO) {
            tracksDatabase.playListDao().insertOnePlayList(playList)
        }

    }


}