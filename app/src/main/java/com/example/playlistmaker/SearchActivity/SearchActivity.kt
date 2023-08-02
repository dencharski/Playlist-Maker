package com.example.playlistmaker.SearchActivity

import android.content.Context
import android.content.Intent
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
import com.example.playlistmaker.AudioPlayerActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.SettingsActivity
import com.example.playlistmaker.Track
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.internet.ITunesSearchInterface
import com.example.playlistmaker.internet.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity(), TrackListAdapter.ItemClickInterface,
    TrackListAdapterHistory.ItemClickInterfaceHistory {

    private lateinit var binding: ActivitySearchBinding

    private var searchHistory: SearchHistory? = null

    private var editTextSearch: EditText? = null
    private var clearButton: ImageView? = null
    private var goBackButton: ImageView? = null
    private var trackListAdapter: TrackListAdapter? = null
    private var trackListAdapterHistory: TrackListAdapterHistory? = null
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
        private const val teg = "SearchActivity"
        private const val key: String = "key"
        private const val baseUrl = "https://itunes.apple.com"
        private val trackList = arrayListOf<Track>()
        private const val codeSuccess = 200

        const val trackId = "trackId"
        const val trackName = "trackName"
        const val artistName="artistName"
        const val trackTimeMillis = "trackTimeMillis"
        const val artworkUrl100 = "artworkUrl100"
        const val collectionName = "collectionName"
        const val releaseDate = "releaseDate"
        const val primaryGenreName = "primaryGenreName"
        const val country = "country"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val sharedPrefs =
            getSharedPreferences(SettingsActivity.PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)
        searchHistory = SearchHistory(sharedPrefs)

        editTextSearch = binding.editTextSearch
        clearButton = binding.imageViewClear
        goBackButton = binding.imageViewBackArrow
        recyclerViewTrackList = binding.recyclerViewTrackList
        layoutEmptyResult = binding.layoutEmptyResult
        layoutErrorInternetConnection = binding.layoutNoInternetConnection
        layoutRecyclerView = binding.layoutRecyclerView
        buttonRefresh = binding.buttonRefresh

        trackListAdapter = TrackListAdapter()
        trackListAdapter?.setInItemClickListener(this)
        recyclerViewTrackList?.adapter = trackListAdapter

        trackListAdapterHistory = TrackListAdapterHistory()
        trackListAdapterHistory?.setInItemClickListener(this)

        searchHistory?.getTrackList()?.let { trackListAdapterHistory?.setTrackList(it) }
        binding.recyclerViewTrackListHistory.adapter = trackListAdapterHistory

        binding.buttonCleanHistory.setOnClickListener {
            searchHistory?.removeTrackListInSharedPreferences()
            binding.layoutSearchHistory.visibility = View.GONE

        }

        clearButton?.setOnClickListener {
            editTextSearch?.setText("")

            layoutEmptyResult?.visibility = View.GONE
            layoutErrorInternetConnection?.visibility = View.GONE

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

                if (editTextSearch?.hasFocus() == true && s?.isEmpty() == true) {
                    Log.d(teg, "TextChangedListener focus - true")

                    if (trackListAdapterHistory?.itemCount != 0) {
                        binding.layoutSearchHistory.visibility = View.VISIBLE
                        layoutRecyclerView?.visibility = View.GONE
                    }

                } else {
                    Log.d(teg, "TextChangedListener focus - false")
                    binding.layoutSearchHistory.visibility = View.GONE
                    layoutRecyclerView?.visibility = View.VISIBLE
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

        editTextSearch?.setOnFocusChangeListener { view, hasFocus ->

            if (hasFocus && editTextSearch?.text?.isEmpty() == true) {
                Log.d(teg, "focus - true")
                if (trackListAdapterHistory?.itemCount != 0) {
                    binding.layoutSearchHistory.visibility = View.VISIBLE
                    layoutRecyclerView?.visibility = View.GONE
                }

            } else {
                Log.d(teg, "focus - false")

            }
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

    override fun onItemClick(track: Track) {
        Log.d(teg, "adapterClick ${track.trackId}")
        searchHistory?.writeOneTrack(track)
        searchHistory?.getTrackList()?.let { trackListAdapterHistory?.setTrackList(it) }

        val intent = Intent(
            this,
            AudioPlayerActivity::class.java
        )
        intent.putExtra(trackId, track.trackId.toString())
        intent.putExtra(trackName, track.trackName)
        intent.putExtra(artistName, track.artistName)
        intent.putExtra(trackTimeMillis, track.trackTimeMillis)
        intent.putExtra(artworkUrl100, track.artworkUrl100)
        intent.putExtra(collectionName, track.collectionName)
        intent.putExtra(releaseDate, track.releaseDate)
        intent.putExtra(primaryGenreName, track.primaryGenreName)
        intent.putExtra(country, track.country)

        startActivity(intent)
    }

    override fun onItemClickHistory(track: Track) {
        Log.d(teg, "adapterClickHistory ${track.trackId}")

        Log.d(
            teg,
            "adapterClickHistory ${Track::trackId.toString()}," +
                    " ${Track::trackName.toString()}, " +
                    "${Track::trackTimeMillis.toString()}," +
                    " ${Track::artworkUrl100.toString()}, " +
                    "${Track::collectionName.toString()}, ${Track::releaseDate.toString()}," +
                    " ${Track::primaryGenreName.toString()}, ${Track::country.toString()}"
        )
        val intent = Intent(
            this,
            AudioPlayerActivity::class.java
        )
        intent.putExtra(trackId, track.trackId.toString())
        intent.putExtra(trackName, track.trackName)
        intent.putExtra(artistName, track.artistName)
        intent.putExtra(trackTimeMillis, track.trackTimeMillis)
        intent.putExtra(artworkUrl100, track.artworkUrl100)
        intent.putExtra(collectionName, track.collectionName)
        intent.putExtra(releaseDate, track.releaseDate)
        intent.putExtra(primaryGenreName, track.primaryGenreName)
        intent.putExtra(country, track.country)

        startActivity(intent)
    }

}