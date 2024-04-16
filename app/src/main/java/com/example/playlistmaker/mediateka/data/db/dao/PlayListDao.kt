package com.example.playlistmaker.mediateka.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.playlistmaker.mediateka.data.db.PlayListEntity


@Dao
interface PlayListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOnePlayList(playList: PlayListEntity)

    @Delete
    fun deleteOnePlayList(playList: PlayListEntity)

    @Query("SELECT * FROM playlist_table")
    fun getPlayLists(): List<PlayListEntity>
}