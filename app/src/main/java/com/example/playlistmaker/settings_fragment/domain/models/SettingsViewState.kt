package com.example.playlistmaker.settings_fragment.domain.models

sealed class SettingsViewState {
    object Light : SettingsViewState()

    object Dark : SettingsViewState()
}