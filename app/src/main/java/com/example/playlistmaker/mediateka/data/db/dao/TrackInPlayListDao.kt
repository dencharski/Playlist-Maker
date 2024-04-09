package com.example.playlistmaker.mediateka.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.mediateka.data.db.TrackEntity

@Dao
interface TrackInPlayListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneTrack(track: TrackEntity)

    @Delete
    fun deleteOneTrack(track: TrackEntity)

    @Query("SELECT trackId FROM track_in_playlist_table")
    fun getTracksIds(): List<String>

    @Query("SELECT * FROM track_in_playlist_table")
    fun getTracks(): List<TrackEntity>
}