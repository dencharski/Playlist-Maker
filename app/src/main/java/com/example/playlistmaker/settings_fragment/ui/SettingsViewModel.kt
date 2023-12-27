package com.example.playlistmaker.settings_fragment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.settings_fragment.domain.models.SettingsViewState
import com.example.playlistmaker.settings_fragment.domain.api.SettingsInteractor

class SettingsViewModel(private val settingsInteractorImpl: SettingsInteractor) :
    ViewModel() {
    private val _isDarkTheme = MutableLiveData<SettingsViewState>()
    val isDarkTheme: LiveData<SettingsViewState> get() = _isDarkTheme

    init {
        setIsDarkTheme(settingsInteractorImpl.getDarkTheme())
    }

    fun setIsDarkTheme(darkThemeEnabled: Boolean) {
        if (settingsInteractorImpl.switchTheme(darkThemeEnabled)) {
            _isDarkTheme.postValue(SettingsViewState.Dark)
        } else {
            _isDarkTheme.postValue(SettingsViewState.Light)
        }
    }

}