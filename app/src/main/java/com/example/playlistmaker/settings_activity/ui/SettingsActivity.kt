package com.example.playlistmaker.settings_activity.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.R
import com.example.playlistmaker.settings_activity.data.dto.SettingsViewState
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val settingsViewModel by viewModel<SettingsViewModel>()

    companion object {
        const val PRACTICUM_EXAMPLE_PREFERENCES = "practicum_example_preferences"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        observeValues()

        binding.switchDarkTheme.setOnCheckedChangeListener { _, checked ->
            settingsViewModel.setIsDarkTheme(checked)
        }

        binding.imageViewBackArrow.setOnClickListener { finish() }

        binding.layoutSharedApp.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = getString(R.string.intent_type)
            intent.putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.android_course_url)
            )
            startActivity(intent)
        }

        binding.layoutWriteSupport.setOnClickListener {
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

        binding.layoutUserAgreement.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse(getString(R.string.practicum_offer))
            startActivity(intent)

        }
    }

    private fun observeValues() {
        settingsViewModel.isDarkTheme.observe(this) {
            when (it) {
                SettingsViewState.Dark -> binding.switchDarkTheme.isChecked = true
                SettingsViewState.Light -> binding.switchDarkTheme.isChecked = false
            }

        }
    }
}