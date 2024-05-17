package com.example.playlistmaker.audio_player.data

import com.example.playlistmaker.create_playlist.data.CreatePlayListDbConvertor
import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.mediateka.data.db.PlayListEntity
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerPlayListRepository
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
        val playListEntity = createPlayListDbConvertor.mapConvertPlayList(playList)
        val playListEntityInsert = convertAndAddTrackIdInPlayList(playListEntity, trackId)

        withContext(Dispatchers.IO) {
            tracksDatabase.playListDao().addOnePlayList(playListEntityInsert)
        }
    }

    override suspend fun deleteTrackIdInPlayList(playList: PlayList, trackId: Long) {
        return withContext(Dispatchers.IO) {
            val playListEntity = createPlayListDbConvertor.mapConvertPlayList(playList)
            val playListEntityInsert = convertAndDeleteTrackIdInPlayList(playListEntity, trackId)
            tracksDatabase.playListDao().addOnePlayList(playListEntityInsert)
        }
    }

    override suspend fun deletePlayList(playList: PlayList) {
        withContext(Dispatchers.IO) {
            tracksDatabase.playListDao()
                .deleteOnePlayList(createPlayListDbConvertor.mapConvertPlayList(playList))
        }
    }

    override suspend fun checkTrackIdInAllPlayListsTrackIds(trackId: Long): Boolean {
        return withContext(Dispatchers.IO) {
            convertListIdStringToListIdLong(
                tracksDatabase.playListDao().getAllTracksIdsFromAllPlayLists()
            ).contains(trackId)
        }
    }


    override suspend fun getPlayList(playListId: Long): PlayList {
        return withContext(Dispatchers.IO) {
            val playListEntity = tracksDatabase.playListDao().getPlayList(playListId)
            createPlayListDbConvertor.map(playListEntity)
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

    private fun convertAndDeleteTrackIdInPlayList(
        playList: PlayListEntity,
        trackId: Long
    ): PlayListEntity {
        val listOfTrackIds: MutableList<Long> =
            createPlayListDbConvertor.stringInArrayOfLong(playList.listOfTrackIds).toMutableList()
        listOfTrackIds.remove(trackId)

        return PlayListEntity(
            playListId = playList.playListId,
            playListName = playList.playListName,
            playlistDescription = playList.playlistDescription,
            playlistImageUri = playList.playlistImageUri,
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

    private fun convertListIdStringToListIdLong(list: List<String>): List<Long> {
        val listLong = mutableListOf<Long>()
        list.forEach { it -> listLong.addAll(createPlayListDbConvertor.stringInArrayOfLong(it)) }
        return listLong
    }
}