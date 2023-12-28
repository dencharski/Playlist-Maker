package com.example.playlistmaker.settings.domain.api

interface SettingsInteractor {
     fun switchTheme(darkThemeEnabled: Boolean):Boolean
     fun getDarkTheme():Boolean
}