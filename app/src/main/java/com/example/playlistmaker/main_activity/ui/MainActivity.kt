package com.example.playlistmaker.main_activity.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.mediateka_activity.ui.MediatekaActivity
import com.example.playlistmaker.search_activity.ui.SearchActivity
import com.example.playlistmaker.settings_activity.ui.SettingsActivity
import com.example.playlistmaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModel.getViewModelFactory()
        )[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonSearch.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    SearchActivity::class.java
                )
            )
        }

        binding.buttonMediateka.setOnClickListener {
            val intent = Intent(this, MediatekaActivity::class.java)
            startActivity(intent)
        }

        binding.buttonSettings.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                )
            )
        }
    }

}