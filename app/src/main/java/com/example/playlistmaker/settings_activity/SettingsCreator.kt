package com.example.playlistmaker.settings_activity

import com.example.playlistmaker.settings_activity.data.SettingsRepositoryImpl
import com.example.playlistmaker.settings_activity.domain.impl.SettingsInteractorImpl
import com.example.playlistmaker.settings_activity.domain.api.SettingsInteractor
import com.example.playlistmaker.settings_activity.domain.api.SettingsRepository

object SettingsCreator {

    fun getSettingsInteractor(): SettingsInteractor {
        return SettingsInteractorImpl(
            getSettingsRepository()
        )
    }

    private fun getSettingsRepository(): SettingsRepository {
        return SettingsRepositoryImpl()
    }
}