package com.example.playlistmaker.main_activity.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.main_activity.domain.api.MainInteractor

class MainViewModel(private val mainInteractor: MainInteractor) : ViewModel() {



    fun setTheme(){

        mainInteractor.setTheme()
    }

}