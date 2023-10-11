package com.example.playlistmaker.settings_activity.data.dto

sealed class SettingsViewState {
    object Light : SettingsViewState()

    object Dark : SettingsViewState()
}