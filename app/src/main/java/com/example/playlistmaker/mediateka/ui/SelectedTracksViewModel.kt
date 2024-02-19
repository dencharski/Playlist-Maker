package com.example.playlistmaker.mediateka.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.mediateka.domain.api.SelectedTrackInteractor
import com.example.playlistmaker.mediateka.domain.models.SelectedTracksViewState
import kotlinx.coroutines.launch

class SelectedTracksViewModel(private val selectedTrackInteractor: SelectedTrackInteractor) :
    ViewModel() {
    private val _selectedTracksViewState = MutableLiveData<SelectedTracksViewState>()
    val selectedTracksViewState get() = _selectedTracksViewState

    init {
        Log.d("tag", "init fun getSelectedTracks()")
    }

    fun getSelectedTracks() {

        viewModelScope.launch {
            selectedTrackInteractor.getTracks().collect {
                if (it.isEmpty()) {
                    _selectedTracksViewState.postValue(SelectedTracksViewState.ListSelectedTracksEmpty)
                } else {
                    _selectedTracksViewState.postValue(
                        SelectedTracksViewState.ListSelectedTracks(
                            list = it
                        )
                    )
                }
            }

        }
    }
}