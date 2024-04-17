package com.example.playlistmaker.mediateka.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.mediateka.data.db.TrackInPlayListEntity

@Dao
interface TrackInPlayListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOneTrackInPlayList(track: TrackInPlayListEntity)

    @Delete
    fun deleteOneTrackFromPlayList(track: TrackInPlayListEntity)

    @Query("SELECT trackId FROM track_in_playlist_table")
    fun getTracksIdsFromPlayList(): List<String>

    @Query("SELECT * FROM track_in_playlist_table")
    fun getTracksFromPlayList(): List<TrackInPlayListEntity>
}