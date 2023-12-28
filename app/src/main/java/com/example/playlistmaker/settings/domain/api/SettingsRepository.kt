package com.example.playlistmaker.settings.domain.api

interface SettingsRepository {
    fun switchTheme(darkThemeEnabled: Boolean):Boolean
    fun getDarkTheme():Boolean
}