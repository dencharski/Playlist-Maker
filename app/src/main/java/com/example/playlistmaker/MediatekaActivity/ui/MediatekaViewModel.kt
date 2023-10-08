package com.example.playlistmaker.MediatekaActivity.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.MainActivity.ui.MainViewModel

class MediatekaViewModel:ViewModel() {

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MediatekaViewModel()
            }
        }

    }
}