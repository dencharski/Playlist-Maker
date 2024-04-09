package com.example.playlistmaker.mediateka.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.mediateka.data.db.dao.PlayListDao
import com.example.playlistmaker.mediateka.data.db.dao.TrackDao
import com.example.playlistmaker.mediateka.data.db.dao.TrackInPlayListDao

@Database(
    version = 2,
    entities = [TrackEntity::class, PlayListEntity::class, TrackInPlayListEntity::class]
)
abstract class TracksDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun playListDao(): PlayListDao
    abstract fun trackInPlayListDao(): TrackInPlayListDao
}