package com.example.playlistmaker.main_activity.domain

import android.util.Log
import com.example.playlistmaker.main_activity.domain.api.MainInteractor
import com.example.playlistmaker.main_activity.domain.api.MainRepository

class MainInteractorImpl(private val mainRepositoryImpl: MainRepository):MainInteractor {
    override fun setTheme() {
        mainRepositoryImpl.setTheme()
    }
}