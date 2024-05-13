package com.example.playlistmaker.create_playlist.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.create_playlist.domain.api.CreatePlayListInteractor
import com.example.playlistmaker.create_playlist.domain.models.CreatePlayListViewState
import com.example.playlistmaker.create_playlist.domain.models.PlayList
import kotlinx.coroutines.launch

class CreatePlaylistViewModel(private val createPlayListInteractor: CreatePlayListInteractor) :
    ViewModel() {

    private val _createPlayListState = MutableLiveData<CreatePlayListViewState>()
    val createPlayListState: LiveData<CreatePlayListViewState> get() = _createPlayListState

    fun createPlaylist(name: String, description: String, imageUri: String) {
        viewModelScope.launch {
            createPlayListInteractor.createPlaylist(
                PlayList(
                    playListId = null,
                    playListName = name,
                    playlistDescription = description,
                    playlistImageUri = imageUri,
                    listOfTrackIds = mutableListOf<Long>(),
                    sizeOfTrackIdsList = 0
                )
            )
        }

    }

    fun editPlaylist(name: String, description: String, imageUri: String, playList:PlayList) {
        viewModelScope.launch {
            createPlayListInteractor.editPlaylist(
                PlayList(
                    playListId = playList.playListId,
                    playListName = name,
                    playlistDescription = description,
                    playlistImageUri = imageUri,
                    listOfTrackIds = playList.listOfTrackIds,
                    sizeOfTrackIdsList = playList.listOfTrackIds.size
                )
            )
        }

    }

    fun setPlayList(playList: PlayList) {
        _createPlayListState.value = CreatePlayListViewState.EditPlayList(playList = playList)
    }
}