package com.example.playlistmaker.settings_fragment.domain.api

interface SettingsInteractor {
     fun switchTheme(darkThemeEnabled: Boolean):Boolean
     fun getDarkTheme():Boolean
}