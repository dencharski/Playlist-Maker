package com.example.playlistmaker.MediatekaActivity.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.MainActivity.ui.MainViewModel
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityMediatekaBinding

class MediatekaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediatekaBinding
    private var mediatekaViewModel: MediatekaViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediatekaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mediatekaViewModel =
            ViewModelProvider(
                this,
                MediatekaViewModel.getViewModelFactory()
            )[MediatekaViewModel::class.java]
    }
}