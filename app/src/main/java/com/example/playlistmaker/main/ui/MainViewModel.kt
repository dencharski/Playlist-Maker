package com.example.playlistmaker.main.ui


import androidx.lifecycle.ViewModel
import com.example.playlistmaker.main.domain.api.MainInteractor

class MainViewModel(private val mainInteractor: MainInteractor) : ViewModel() {
    fun setTheme(){
        mainInteractor.setTheme()
    }

}