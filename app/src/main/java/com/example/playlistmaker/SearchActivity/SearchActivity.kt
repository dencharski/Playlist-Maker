package com.example.playlistmaker.SearchActivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.Track
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.internet.ITunesSearchInterface
import com.example.playlistmaker.internet.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private var binding: ActivitySearchBinding? = null

    private var editTextSearch: EditText? = null
    private var clearButton: ImageView? = null
    private var goBackButton: ImageView? = null
    private var trackListAdapter: TrackListAdapter? = null
    private var recyclerViewTrackList: RecyclerView? = null
    private var layoutEmptyResult: LinearLayout? = null
    private var layoutErrorInternetConnection: LinearLayout? = null
    private var layoutRecyclerView: FrameLayout? = null
    private var buttonRefresh: Button? = null

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val iTunesSearchInterface = retrofit.create(ITunesSearchInterface::class.java)


    companion object {
        private const val key: String = "key"
        private const val baseUrl = "https://itunes.apple.com"
        private val trackList = arrayListOf<Track>()
        private const val codeSuccess = 200
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)


        editTextSearch = binding?.editTextSearch
        clearButton = binding?.imageViewClear
        goBackButton = binding?.imageViewBackArrow
        recyclerViewTrackList = binding?.recyclerViewTrackList
        layoutEmptyResult = binding?.layoutEmptyResult
        layoutErrorInternetConnection = binding?.layoutNoInternetConnection
        layoutRecyclerView = binding?.layoutRecyclerView
        buttonRefresh = binding?.buttonRefresh

        trackListAdapter = TrackListAdapter()
        trackListAdapter?.setTrackList(trackList)
        recyclerViewTrackList?.adapter = trackListAdapter

        clearButton?.setOnClickListener {
            editTextSearch?.setText("")
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(editTextSearch?.windowToken, 0)
        }
        goBackButton?.setOnClickListener { finish() }

        buttonRefresh?.setOnClickListener { searchTrack(editTextSearch?.text.toString()) }

        editTextSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    clearButton?.visibility = View.GONE
                    trackListAdapter?.setTrackList(arrayListOf())
                } else {
                    clearButton?.visibility = View.VISIBLE


                }

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        editTextSearch?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                searchTrack(editTextSearch?.text.toString())

                true
            }
            false
        }
    }

    private fun searchTrack(text: String) {

        getNewRequest()

        iTunesSearchInterface
            .search(text)
            .enqueue(object : Callback<ResponseModel> {
                override fun onResponse(
                    call: Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                    Log.d("TRANSLATION_LOG", "Status code: ${response.code()}")

                    if (response.code() == codeSuccess) {

                        if (response.body()?.results?.size != 0) {
                            Log.d(
                                "TRANSLATION_LOG",
                                "response.body()?.results?.size: ${response.body()?.results?.size}"
                            )
                            response.body()?.results?.forEach {
                                Log.d(
                                    "TRANSLATION_LOG",
                                    " item: ${it.trackName}, ${it.artistName}, ${it.trackTimeMillis.toLongOrNull()}, ${it.artworkUrl100}"
                                )
                            }

                            trackList.clear()
                            response.body()?.results?.let { trackList.addAll(it) }
                            trackListAdapter?.setTrackList(trackList)
                        } else {
                            Log.d(
                                "TRANSLATION_LOG",
                                "response.body()?.results?.size == 0: ${response.body()?.results?.size}"
                            )

                            showEmptyResult()

                        }
                    } else {
                        Log.d("TRANSLATION_LOG", "Trouble status code: ${response.code()}")
                        showErrorResult()
                    }

                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    Log.d("TRANSLATION_LOG", "Trouble status code: ${t.message}")
                    showErrorResult()
                }

            })

    }

    private fun getNewRequest() {
        layoutEmptyResult?.visibility = View.GONE
        layoutErrorInternetConnection?.visibility = View.GONE
        layoutRecyclerView?.visibility = View.VISIBLE
    }

    private fun showEmptyResult() {
        layoutEmptyResult?.visibility = View.VISIBLE
        layoutErrorInternetConnection?.visibility = View.GONE
        layoutRecyclerView?.visibility = View.GONE
    }

    private fun showErrorResult() {
        layoutErrorInternetConnection?.visibility = View.VISIBLE
        layoutEmptyResult?.visibility = View.GONE
        layoutRecyclerView?.visibility = View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString(key, editTextSearch?.text.toString())
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        editTextSearch?.setText(savedInstanceState?.getString(key))
    }

}