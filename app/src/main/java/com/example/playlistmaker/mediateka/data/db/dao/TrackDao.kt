package com.example.playlistmaker.mediateka.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.mediateka.data.db.TrackEntity

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneTrack(track: TrackEntity)

    @Delete
    fun deleteOneTrack(track: TrackEntity)

    @Query("SELECT trackId FROM track_table")
    fun getTracksIds(): List<String>

    @Query("SELECT * FROM track_table")
    fun getTracks(): List<TrackEntity>
}