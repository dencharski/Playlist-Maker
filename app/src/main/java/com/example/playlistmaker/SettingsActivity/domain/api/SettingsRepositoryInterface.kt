package com.example.playlistmaker.SettingsActivity.domain.api

interface SettingsRepositoryInterface {
    fun switchTheme(darkThemeEnabled: Boolean):Boolean
}