package com.example.playlistmaker.SettingsActivity.domain.impl

import com.example.playlistmaker.SettingsActivity.data.SettingsRepositoryImpl
import com.example.playlistmaker.SettingsActivity.domain.api.SettingsInteractorInterface
import com.example.playlistmaker.SettingsActivity.domain.api.SettingsRepositoryInterface

class SettingsInteractorImpl(private val settingsRepositoryImpl: SettingsRepositoryInterface) :
    SettingsInteractorInterface {
    override fun switchTheme(darkThemeEnabled: Boolean): Boolean {
        return settingsRepositoryImpl.switchTheme(darkThemeEnabled)
    }
}