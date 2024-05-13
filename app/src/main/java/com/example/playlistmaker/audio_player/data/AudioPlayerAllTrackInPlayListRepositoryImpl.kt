package com.example.playlistmaker.audio_player.data

import android.util.Log
import com.example.playlistmaker.create_playlist.data.TrackInPlayListConvertor
import com.example.playlistmaker.main.domain.models.TrackApp
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerAllTrackInPlayListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class AudioPlayerAllTrackInPlayListRepositoryImpl(
    private val tracksDatabase: TracksDatabase,
    private val trackInPlayListConvertor: TrackInPlayListConvertor
) :
    AudioPlayerAllTrackInPlayListRepository {
    override suspend fun addTrackInPlayListTable(track: TrackApp) {

        withContext(Dispatchers.IO) {
            tracksDatabase.trackInPlayListDao()
                .addOneTrackInPlayList(trackInPlayListConvertor.map(track))
        }

    }

    override suspend fun getAllTracksInPlayList(listOfTrackId: List<Long>): Flow<List<TrackApp>> =
        flow {
            emit(withContext(Dispatchers.IO) {
                tracksDatabase.trackInPlayListDao()
                    .getTracksFromPlayList(listOfLongInListOfString(listOfTrackId))
                    .map { track -> trackInPlayListConvertor.map(track) }
                    .getSortedList(listOfTrackId)
                    .reversed()

            })
        }

    override suspend fun deleteOneTrackFromPlayList(trackId: Long) {
        withContext(Dispatchers.IO) {
            tracksDatabase.trackInPlayListDao().deleteOneTrackFromPlayList(trackId)
        }
    }

    private fun listOfLongInListOfString(list: List<Long>): List<String> {
        val stringList = mutableListOf<String>("")
        list.forEach { it -> stringList.add(it.toString()) }
        return stringList
    }

    private fun List<TrackApp>.getSortedList(list: List<Long>): List<TrackApp> {
        val trackAppList = mutableListOf<TrackApp>()
        list.forEach { it ->
            this.forEach { element ->
                if (element.trackId == it) {
                    trackAppList.add(element)
                }
            }
        }
        return trackAppList
    }
}
