package com.example.playlistmaker.create_playlist.data

import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.mediateka.data.db.PlayListEntity
import com.google.gson.Gson

class CreatePlayListDbConvertor {
    fun mapCreatePlayList(playList: PlayList): PlayListEntity {
        return PlayListEntity(
            null,
            playListName = playList.playListName,
            playlistDescription = playList.playlistDescription,
            playlistImageUri = playList.playlistImageUri,
            listOfTrackIds = listOfLongInString(playList.listOfTrackIds),
            sizeOfTrackIdsList = playList.listOfTrackIds.size.toString()
        )
    }

    fun mapChangePlayList(playList: PlayList): PlayListEntity {

        return PlayListEntity(
            playListId = playList.playListId.toLong(),
            playListName = playList.playListName,
            playlistDescription = playList.playlistDescription,
            playlistImageUri = playList.playlistImageUri,
            listOfTrackIds = listOfLongInString(playList.listOfTrackIds),
            sizeOfTrackIdsList = playList.listOfTrackIds.size.toString()
        )
    }


    fun map(playList: PlayListEntity): PlayList {
        return PlayList(
            playListId = playList.playListId.toString(),
            playListName = playList.playListName,
            playlistDescription = playList.playlistDescription,
            playlistImageUri = playList.playlistImageUri,
            listOfTrackIds = stringInArrayOfLong(playList.listOfTrackIds).toMutableList(),
            sizeOfTrackIdsList = playList.sizeOfTrackIdsList.toInt()
        )
    }

    fun getListOfTrackIds(playList: PlayList)=playList.listOfTrackIds.toList()

     fun listOfLongInString(list: List<Long>): String {
        return Gson().toJson(list)
    }

      fun stringInArrayOfLong(string: String): Array<Long> {
        return Gson().fromJson(string, Array<Long>::class.java)
    }

}