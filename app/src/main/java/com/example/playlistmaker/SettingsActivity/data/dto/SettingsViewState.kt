package com.example.playlistmaker.SettingsActivity.data.dto

sealed class SettingsViewState {
    object Light : SettingsViewState()

    object Dark : SettingsViewState()
}