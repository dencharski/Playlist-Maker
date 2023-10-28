package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.di.DependencyModule.dataModule
import com.example.playlistmaker.di.DependencyModule.interactorModule
import com.example.playlistmaker.di.DependencyModule.repositoryModule
import com.example.playlistmaker.di.DependencyModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }
    }

    companion object {
        const val trackKey = "trackKey"
        const val THEME_KEY = "THEME_KEY"

        const val STATE_DEFAULT_COMPLETED = 0
        const val STATE_PREPARED = 1
        const val STATE_PLAYING = 2
        const val STATE_PAUSED = 3

    }

}