package com.example.playlistmaker.SettingsActivity.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.SettingsActivity.SettingsCreator
import com.example.playlistmaker.SettingsActivity.data.dto.SettingsViewState
import com.example.playlistmaker.SettingsActivity.domain.api.SettingsInteractorInterface

class SettingsViewModel(private val settingsInteractorImpl: SettingsInteractorInterface) :
    ViewModel() {
    private val _isDarkTheme = MutableLiveData<SettingsViewState>()
    val isDarkTheme: LiveData<SettingsViewState> get() = _isDarkTheme

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val settingsInteractorImpl = SettingsCreator.getSettingsInteractor()
                SettingsViewModel(settingsInteractorImpl)
            }
        }
    }

    fun setIsDarkTheme(darkThemeEnabled: Boolean) {

        if (settingsInteractorImpl.switchTheme(darkThemeEnabled)){
            _isDarkTheme.postValue(SettingsViewState.Dark)
        }else{
            _isDarkTheme.postValue(SettingsViewState.Light)
        }
    }

}