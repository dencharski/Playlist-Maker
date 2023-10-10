package com.example.playlistmaker.mediateka_activity.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.databinding.ActivityMediatekaBinding

class MediatekaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediatekaBinding
    private val mediatekaViewModel: MediatekaViewModel by lazy {
        ViewModelProvider(
            this,
            MediatekaViewModel.getViewModelFactory()
        )[MediatekaViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediatekaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
}