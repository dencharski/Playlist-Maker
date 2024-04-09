package com.example.playlistmaker.create_playlist.data

import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.mediateka.data.db.PlayListEntity
import com.google.gson.Gson

class CreatePlayListDbConvertor {
    fun map(playList: PlayList): PlayListEntity {
        return PlayListEntity(
            null,
            playListName = playList.playListName,
            playlistDescription = playList.playlistDescription,
            playlistImageUri = playList.playlistImageUri,
            listOfTrackIds = listOfTracksInString(playList.listOfTrackIds),
            sizeOfTrackIdsList = playList.sizeOfTrackIdsList.toString()
        )
    }

    fun mapInsertChangePlayList(playList: PlayList, trackId: Long): PlayListEntity {
        val list = addTrackIdInList(playList, trackId)
        return PlayListEntity(
            playListId = list.playListId.toLong(),
            playListName = list.playListName,
            playlistDescription = list.playlistDescription,
            playlistImageUri = list.playlistImageUri,
            listOfTrackIds = listOfTracksInString(list.listOfTrackIds),
            sizeOfTrackIdsList = list.sizeOfTrackIdsList.toString()
        )
    }

    private fun addTrackIdInList(
        playList: PlayList,
        trackId: Long
    ): PlayList {
        playList.listOfTrackIds.add(trackId)
        playList.sizeOfTrackIdsList = playList.listOfTrackIds.size
        return playList
    }

    fun map(playList: PlayListEntity): PlayList {
        return PlayList(
            playListId = playList.playListId.toString(),
            playListName = playList.playListName,
            playlistDescription = playList.playlistDescription,
            playlistImageUri = playList.playlistImageUri,
            listOfTrackIds = stringInListOfLong(playList.listOfTrackIds).toMutableList(),
            sizeOfTrackIdsList = playList.sizeOfTrackIdsList.toInt()
        )
    }

    private fun listOfTracksInString(list: List<Long>): String {
        return Gson().toJson(list)
    }

    private fun stringInListOfLong(string: String): Array<Long> {
        return Gson().fromJson(string, Array<Long>::class.java)
    }

}