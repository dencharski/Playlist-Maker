package com.example.playlistmaker.settings_activity.domain.impl

import com.example.playlistmaker.settings_activity.domain.api.SettingsInteractor
import com.example.playlistmaker.settings_activity.domain.api.SettingsRepository

class SettingsInteractorImpl(private val settingsRepositoryImpl: SettingsRepository) :
    SettingsInteractor {
    override fun switchTheme(darkThemeEnabled: Boolean): Boolean {
        return settingsRepositoryImpl.switchTheme(darkThemeEnabled)
    }
}