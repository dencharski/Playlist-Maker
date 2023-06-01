package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout

class SettingsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val imageViewBackArrow: ImageView = findViewById<ImageView>(R.id.image_view_back_arrow)
        imageViewBackArrow.setOnClickListener { finish() }

        val buttonShare: LinearLayout = findViewById(R.id.layout_shared_app)
        buttonShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = getString(R.string.intent_type)
            intent.putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.android_course_url)
            )
            startActivity(intent)
        }

        val buttonWriteSupport: LinearLayout = findViewById(R.id.layout_write_support)
        buttonWriteSupport.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SENDTO
            intent.data = Uri.parse(getString(R.string.url_string))
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.mail_address)))
            intent.putExtra(
                Intent.EXTRA_SUBJECT,
                getString(R.string.mail_title)
            )
            intent.putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.mail_text)
            )
            startActivity(intent)
        }
        val buttonUserAgreement: LinearLayout = findViewById(R.id.layout_user_agreement)
        buttonUserAgreement.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(getString(R.string.practicum_offer))
            startActivity(intent)

        }
    }
}