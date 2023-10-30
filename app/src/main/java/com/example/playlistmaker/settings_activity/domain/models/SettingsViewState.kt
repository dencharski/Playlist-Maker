package com.example.playlistmaker.settings_activity.domain.models

sealed class SettingsViewState {
    object Light : SettingsViewState()

    object Dark : SettingsViewState()
}