package com.example.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener

class SearchActivity : AppCompatActivity() {

    var editTextSearch: EditText? = null
    var clearButton: ImageView? = null
    var goBackButton: ImageView? = null
    private val key:String="key"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        editTextSearch = findViewById<EditText>(R.id.edit_text_search)
        clearButton = findViewById<ImageView>(R.id.image_view_clear)
        goBackButton = findViewById(R.id.image_view_back_arrow)

        clearButton?.setOnClickListener { editTextSearch?.setText("") }
        goBackButton?.setOnClickListener { finish() }

        editTextSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    clearButton?.visibility = View.GONE
                } else {
                    clearButton?.visibility = View.VISIBLE
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString(key,editTextSearch?.text.toString())
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        editTextSearch?.setText(savedInstanceState?.getString(key))
    }

}