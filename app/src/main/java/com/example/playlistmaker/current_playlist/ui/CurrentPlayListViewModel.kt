package com.example.playlistmaker.current_playlist.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerPlayListInteractor
import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.current_playlist.domain.models.CurrentPlayListViewState
import com.example.playlistmaker.main.domain.models.TrackApp
import kotlinx.coroutines.launch

class CurrentPlayListViewModel(private val audioPlayerPlayListInteractor: AudioPlayerPlayListInteractor) :
    ViewModel() {
    private val _currentPlayListViewState = MutableLiveData<CurrentPlayListViewState>()
    val currentPlayListViewState: LiveData<CurrentPlayListViewState> get() = _currentPlayListViewState
    private val tag = "fragment"
    private var playList: PlayList? = null
    fun getPlayList(playListId: Long) {
        viewModelScope.launch {
            playList = audioPlayerPlayListInteractor.getPlayList(playListId)

            if (playList != null) {
                _currentPlayListViewState.postValue(CurrentPlayListViewState.PlayListState(playList = playList!!))

            }

        }
    }

    fun getPlayListTracks(listOfTracks: List<Long>) {
        viewModelScope.launch {
            audioPlayerPlayListInteractor.getAllTracksInPlayList(listOfTracks).collect {
                if (it.isEmpty()) {
                    _currentPlayListViewState.postValue(
                        CurrentPlayListViewState.EmptyPlayList(
                            error = "no tracks in playlist"
                        )
                    )
                } else {

                    _currentPlayListViewState.postValue(
                        CurrentPlayListViewState.ListOfTracks(
                            listOfTracks = it
                        )
                    )
                }
            }
        }
    }

    fun deleteOneTrackInPlayList(playList: PlayList, trackId: Long) {
        viewModelScope.launch {
            _currentPlayListViewState.postValue(
                CurrentPlayListViewState.DeleteTrackIdFromPlayList(
                    audioPlayerPlayListInteractor.deleteTrackIdInPlayList(playList, trackId)
                )
            )
        }

        checkTrackIdInAllPlayListsTrackIds(trackId)
    }

    fun deletePlayList(playList: PlayList) {
        viewModelScope.launch {
            audioPlayerPlayListInteractor.deletePlayList(playList)
        }
    }
    fun checkListTrackIds(listOfTrackIds:List<TrackApp>){
        listOfTrackIds.forEach { it -> checkTrackIdInAllPlayListsTrackIds(it.trackId) }
    }

    private fun checkTrackIdInAllPlayListsTrackIds(trackId: Long) {
        viewModelScope.launch {
            if (audioPlayerPlayListInteractor.checkTrackIdInAllPlayListsTrackIds(trackId)) {
                Log.d(
                    tag,
                    "vm trackId= ${trackId} is used"
                )
            } else {
                Log.d(
                    tag,
                    "vm trackId= ${trackId} should delete"
                )
                audioPlayerPlayListInteractor.deleteTrackInPlayListTable(trackId)
            }
        }
    }


}