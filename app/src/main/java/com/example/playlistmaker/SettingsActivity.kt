package com.example.playlistmaker

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val imageViewBackArrow:ImageView=findViewById<ImageView>(R.id.image_view_back_arrow)
        imageViewBackArrow.setOnClickListener { finish() }
    }
}