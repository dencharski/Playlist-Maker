package com.example.playlistmaker.mediateka.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.create_playlist.domain.models.PlayList

import com.example.playlistmaker.mediateka.data.db.PlayListEntity


@Dao
interface PlayListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOnePlayList(playList: PlayListEntity)
    @Delete
    fun deleteOnePlayList(playList: PlayListEntity)

    @Query("SELECT * FROM playlist_table")
    fun getPlayLists(): List<PlayListEntity>

    @Query("SELECT * FROM playlist_table WHERE playListId = :playListId")
    fun getPlayList(playListId:Long):PlayListEntity

    @Query("SELECT listOfTrackIds FROM playlist_table")
    fun getAllTracksIdsFromAllPlayLists():List<String>
}