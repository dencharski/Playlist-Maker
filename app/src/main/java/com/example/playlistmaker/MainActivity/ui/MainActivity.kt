package com.example.playlistmaker.MainActivity.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.MediatekaActivity.ui.MediatekaActivity
import com.example.playlistmaker.SearchActivity.ui.SearchActivity
import com.example.playlistmaker.SettingsActivity.ui.SettingsActivity
import com.example.playlistmaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mainViewModel: MainViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainViewModel =
            ViewModelProvider(
                this,
                MainViewModel.getViewModelFactory()
            )[MainViewModel::class.java]


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