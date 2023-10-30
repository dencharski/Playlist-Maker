package com.example.playlistmaker.main_activity.ui


import androidx.lifecycle.ViewModel
import com.example.playlistmaker.main_activity.domain.api.MainInteractor

class MainViewModel(private val mainInteractor: MainInteractor) : ViewModel() {
    fun setTheme(){
        mainInteractor.setTheme()
    }

}