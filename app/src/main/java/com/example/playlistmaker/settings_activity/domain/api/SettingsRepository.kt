package com.example.playlistmaker.settings_activity.domain.api

interface SettingsRepository {
    fun switchTheme(darkThemeEnabled: Boolean):Boolean
    fun getDarkTheme():Boolean
}