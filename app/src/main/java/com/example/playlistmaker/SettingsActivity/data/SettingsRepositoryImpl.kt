package com.example.playlistmaker.SettingsActivity.data

import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.SettingsActivity.domain.api.SettingsRepositoryInterface

class SettingsRepositoryImpl :SettingsRepositoryInterface{

    private var darkTheme = false
    override fun switchTheme(darkThemeEnabled: Boolean):Boolean {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        return darkTheme
    }
}