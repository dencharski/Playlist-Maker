package com.example.playlistmaker.settings_fragment.domain.impl

import com.example.playlistmaker.settings_fragment.domain.api.SettingsInteractor
import com.example.playlistmaker.settings_fragment.domain.api.SettingsRepository

class SettingsInteractorImpl(private val settingsRepositoryImpl: SettingsRepository) :
    SettingsInteractor {
    override fun switchTheme(darkThemeEnabled: Boolean): Boolean {
        return settingsRepositoryImpl.switchTheme(darkThemeEnabled)
    }

    override fun getDarkTheme(): Boolean {
        return settingsRepositoryImpl.getDarkTheme()
    }
}