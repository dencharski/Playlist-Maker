package com.example.playlistmaker.settings_fragment.domain.api

interface SettingsRepository {
    fun switchTheme(darkThemeEnabled: Boolean):Boolean
    fun getDarkTheme():Boolean
}