package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.ACTION_SENDTO
import android.content.Intent.EXTRA_TEXT
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val imageViewBackArrow: ImageView = findViewById<ImageView>(R.id.image_view_back_arrow)
        imageViewBackArrow.setOnClickListener { finish() }

        val imageViewShare: ImageView = findViewById(R.id.image_view_share)
        imageViewShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "https://practicum.yandex.ru/profile/android-developer/"
            )
            startActivity(intent)
        }

        val imageViewWriteSupport: ImageView = findViewById(R.id.image_view_write_support)
        imageViewWriteSupport.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SENDTO
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("dencharski@yandex.ru"))
            intent.putExtra(
                Intent.EXTRA_SUBJECT,
                "Сообщение разработчикам и разработчицам приложения Playlist Maker"
            )
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Спасибо разработчикам и разработчицам за крутое приложение!"
            )
            startActivity(intent)
        }
        val imageViewUserAgreement: ImageView = findViewById(R.id.image_view_user_agreement)
        imageViewUserAgreement.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse("https://yandex.ru/legal/practicum_offer/")
            startActivity(intent)

        }
    }
}