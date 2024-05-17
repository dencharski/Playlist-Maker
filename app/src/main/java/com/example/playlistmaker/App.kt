package com.example.playlistmaker

import android.app.Application
import android.util.Log
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
        const val TRACK_KEY = "TRACK_KEY"
        const val THEME_KEY = "THEME_KEY"
        const val PLAYLIST_ID="PLAYLIST_ID"
        const val PLAYLIST="PLAYLIST"

         fun setTextInPlaylistSize(size: Int): String {
            var tracksString: String = ""

            tracksString = if (size in 10..20) {
                "треков"
            } else {
                when (divideNumber(size)) {
                    0, in 5..9 -> {
                        "треков"
                    }

                    1 -> {
                        "трек"
                    }

                    in 2..4 -> {
                        "трека"
                    }

                    else -> {
                        Log.d(this.javaClass.name, "smth wrong : name exception")
                        "трек"
                    }
                }
            }

            return "$size $tracksString"
        }

        private fun divideNumber(size: Int): Int {
            var number = size

            if (number > 10) {
                number %= 10
                divideNumber(number)
            }
            return number

        }
    }

}