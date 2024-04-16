package com.example.playlistmaker.audio_player.data

import android.util.Log
import com.example.playlistmaker.create_playlist.data.CreatePlayListDbConvertor
import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.mediateka.data.db.PlayListEntity
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerPlayListRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class AudioPlayerPlayListRepositoryImpl(
    private val tracksDatabase: TracksDatabase,
    private val createPlayListDbConvertor: CreatePlayListDbConvertor,
) : AudioPlayerPlayListRepository {
    override suspend fun getListOfPlayLists(): Flow<List<PlayList>> = flow {
        emit(withContext(Dispatchers.IO) {
            tracksDatabase.playListDao().getPlayLists()
                .map { playlist -> convertFromPlayListEntity(playlist) }
        })
    }

    override suspend fun insertTrackIdInPlayList(playList: PlayList, trackId: Long) {
        val playListEntity = createPlayListDbConvertor.mapChangePlayList(playList)
        val playListEntityInsert = convertAndAddTrackIdInPlayList(playListEntity, trackId)

        withContext(Dispatchers.IO) {
            tracksDatabase.playListDao().addOnePlayList(playListEntityInsert)
        }
    }

    private fun convertAndAddTrackIdInPlayList(
        playlist: PlayListEntity,
        trackId: Long
    ): PlayListEntity {
        val listOfTrackIds: MutableList<Long> =
            createPlayListDbConvertor.stringInArrayOfLong(playlist.listOfTrackIds).toMutableList()
        listOfTrackIds.add(trackId)

        return PlayListEntity(
            playListId = playlist.playListId,
            playListName = playlist.playListName,
            playlistDescription = playlist.playlistDescription,
            playlistImageUri = playlist.playlistImageUri,
            listOfTrackIds = createPlayListDbConvertor.listOfLongInString(listOfTrackIds),
            sizeOfTrackIdsList = listOfTrackIds.size.toString()
        )

    }

    override fun checkTrackIdInPlayList(playList: PlayList, trackId: Long): Boolean {
        return createPlayListDbConvertor.getListOfTrackIds(playList).contains(trackId)
    }

    private fun convertFromPlayListEntity(playlist: PlayListEntity): PlayList {
        return createPlayListDbConvertor.map(playlist)
    }


}