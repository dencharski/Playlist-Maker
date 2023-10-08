package com.example.playlistmaker.SettingsActivity

import com.example.playlistmaker.SettingsActivity.data.SettingsRepositoryImpl
import com.example.playlistmaker.SettingsActivity.domain.impl.SettingsInteractorImpl
import com.example.playlistmaker.SettingsActivity.domain.api.SettingsInteractorInterface
import com.example.playlistmaker.SettingsActivity.domain.api.SettingsRepositoryInterface

object SettingsCreator {

    fun getSettingsInteractor(): SettingsInteractorInterface {
        return SettingsInteractorImpl(
            getSettingsRepository()
        )
    }

    private fun getSettingsRepository(): SettingsRepositoryInterface {
        return SettingsRepositoryImpl()
    }
}