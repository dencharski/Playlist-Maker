package com.example.playlistmaker.settings.domain.models

sealed class SettingsViewState {
    object Light : SettingsViewState()

    object Dark : SettingsViewState()
}