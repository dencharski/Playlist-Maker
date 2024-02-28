package com.example.playlistmaker.mediateka.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.mediateka.data.db.dao.TrackDao

@Database(version = 1, entities = [TrackEntity::class])
abstract class TracksDatabase:RoomDatabase() {
    abstract fun trackDao(): TrackDao
}