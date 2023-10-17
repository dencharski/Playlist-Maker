package com.example.playlistmaker.mediateka_activity.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.databinding.ActivityMediatekaBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MediatekaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediatekaBinding
    private val mediatekaViewModel by viewModel<MediatekaViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediatekaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
}