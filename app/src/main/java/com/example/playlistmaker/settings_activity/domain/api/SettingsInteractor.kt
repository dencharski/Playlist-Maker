package com.example.playlistmaker.settings_activity.domain.api

interface SettingsInteractor {
     fun switchTheme(darkThemeEnabled: Boolean):Boolean
     fun getDarkTheme():Boolean
}