package com.example.playlistmaker.create_playlist.data

import com.example.playlistmaker.create_playlist.domain.api.CreatePlayListRepository
import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreatePlayListRepositoryImpl(
    private val tracksDatabase: TracksDatabase,
    private val createPlayListDbConvertor: CreatePlayListDbConvertor
) : CreatePlayListRepository {
    override suspend fun createPlaylist(playList: PlayList) {

        withContext(Dispatchers.IO) {
            tracksDatabase.playListDao().addOnePlayList(playList = createPlayListDbConvertor.mapCreatePlayList(playList))
        }

    }

    override suspend fun editPlaylist(playList: PlayList) {
        withContext(Dispatchers.IO) {
            tracksDatabase.playListDao().addOnePlayList(playList = createPlayListDbConvertor.mapConvertPlayList(playList))
        }
    }
}