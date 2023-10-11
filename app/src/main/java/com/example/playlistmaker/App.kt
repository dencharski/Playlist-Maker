package com.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App:Application() {


    companion object{
        const val trackKey = "trackKey"
    }
    override fun onCreate() {
        super.onCreate()
    }


}