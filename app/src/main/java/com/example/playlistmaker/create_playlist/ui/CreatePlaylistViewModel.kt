package com.example.playlistmaker.create_playlist.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.create_playlist.domain.api.CreatePlayListInteractor
import com.example.playlistmaker.create_playlist.domain.models.PlayList
import kotlinx.coroutines.launch

class CreatePlaylistViewModel(private val createPlayListInteractor: CreatePlayListInteractor) :
    ViewModel() {

    fun createPlaylist(name: String, description: String, imageUri: String) {
        viewModelScope.launch {
            createPlayListInteractor.createPlaylist(
                PlayList(
                    playListId = "",
                    playListName = name,
                    playlistDescription = description,
                    playlistImageUri = imageUri,
                    listOfTrackIds = mutableListOf<Long>(),
                    sizeOfTrackIdsList = 0
                )
            )
        }

    }
}