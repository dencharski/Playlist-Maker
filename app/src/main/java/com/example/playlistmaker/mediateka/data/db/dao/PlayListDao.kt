package com.example.playlistmaker.mediateka.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.playlistmaker.mediateka.data.db.PlayListEntity
import com.example.playlistmaker.mediateka.data.db.TrackEntity

@Dao
interface PlayListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOnePlayList(playList: PlayListEntity)

    @Delete
    fun deleteOnePlayList(track: TrackEntity)

    @Query("SELECT * FROM playlist_table")
    fun getPlayLists(): List<PlayListEntity>
}