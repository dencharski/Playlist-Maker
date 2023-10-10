package com.example.playlistmaker.settings_activity.data

import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.settings_activity.domain.api.SettingsRepository

class SettingsRepositoryImpl :SettingsRepository{

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