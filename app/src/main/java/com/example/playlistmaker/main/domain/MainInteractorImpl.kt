package com.example.playlistmaker.main.domain

import com.example.playlistmaker.main.domain.api.MainInteractor
import com.example.playlistmaker.main.domain.api.MainRepository

class MainInteractorImpl(private val mainRepositoryImpl: MainRepository):MainInteractor {
    override fun setTheme() {
        mainRepositoryImpl.setTheme()
    }
}