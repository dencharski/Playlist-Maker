package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private var binding:ActivitySettingsBinding?=null

    companion object{
        const val PRACTICUM_EXAMPLE_PREFERENCES = "practicum_example_preferences"
        const val EDIT_TEXT_KEY = "key_for_edit_text"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)


        binding?.imageViewBackArrow?.setOnClickListener { finish() }

        binding?.layoutSharedApp?.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = getString(R.string.intent_type)
            intent.putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.android_course_url)
            )
            startActivity(intent)
        }

        if ((applicationContext as App).darkTheme){
            binding?.switchDarkTheme?.isChecked=true
            Log.d("DarkTheme","true")
        }else{
            binding?.switchDarkTheme?.isChecked=false
            Log.d("DarkTheme","false")
        }

        binding?.switchDarkTheme?.setOnCheckedChangeListener{ switcher, checked ->
            (applicationContext as App).switchTheme(checked)
        }

        binding?.layoutWriteSupport?.setOnClickListener {
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

        binding?.layoutUserAgreement?.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(getString(R.string.practicum_offer))
            startActivity(intent)

        }
    }
}