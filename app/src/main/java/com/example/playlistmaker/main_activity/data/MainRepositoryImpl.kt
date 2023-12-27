package com.example.playlistmaker.main_activity.data

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.App
import com.example.playlistmaker.main_activity.domain.api.MainRepository
import com.google.gson.Gson

class MainRepositoryImpl(private val sharedPreferences: SharedPreferences) : MainRepository {
    override fun setTheme() {
        val darkThemeEnabled = readTheme()
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    private fun readTheme(): Boolean {
        val json = sharedPreferences.getString(App.THEME_KEY, false.toString()) ?: return false
        return Gson().fromJson(json, Boolean::class.java)
    }
}