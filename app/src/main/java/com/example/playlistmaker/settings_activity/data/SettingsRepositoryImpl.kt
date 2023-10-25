package com.example.playlistmaker.settings_activity.data

import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.App
import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.search_activity.data.SearchHistoryRepositoryImpl
import com.example.playlistmaker.settings_activity.domain.api.SettingsRepository
import com.google.gson.Gson

class SettingsRepositoryImpl(private val sharedPreferences: SharedPreferences) :
    SettingsRepository {


    override fun switchTheme(darkThemeEnabled: Boolean): Boolean {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                writeTheme(true)
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                writeTheme(false)
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        return readTheme()
    }

    override fun getDarkTheme(): Boolean {
        return readTheme()
    }

    private fun writeTheme(themeBooleanValue: Boolean) {
        val json = Gson().toJson(themeBooleanValue)
        sharedPreferences.edit()
            .putString(App.THEME_KEY, json)
            .apply()

    }

    private fun readTheme(): Boolean {

        val json = sharedPreferences.getString(App.THEME_KEY, false.toString()) ?: return false

        return Gson().fromJson(json, Boolean::class.java)
    }

}