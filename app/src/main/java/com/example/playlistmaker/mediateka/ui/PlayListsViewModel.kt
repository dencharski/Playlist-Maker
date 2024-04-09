package com.example.playlistmaker.mediateka.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.mediateka.domain.api.PlayListInteractor
import com.example.playlistmaker.mediateka.domain.models.PlayListsViewState
import kotlinx.coroutines.launch


class PlayListsViewModel(private val playListInteractor: PlayListInteractor) : ViewModel() {

    private val _playListsViewState = MutableLiveData<PlayListsViewState>()
    val playListsViewState: LiveData<PlayListsViewState> get() = _playListsViewState
    fun getListOfPlayLists() {
        viewModelScope.launch {
            playListInteractor.getListOfPlayLists().collect {
                if (it.isEmpty()){
                    _playListsViewState.value=PlayListsViewState.ListOfPlayListsIsEmpty
                }else{
                    _playListsViewState.value=PlayListsViewState.ListOfPlayLists(it)
                }

            }
        }
    }
}