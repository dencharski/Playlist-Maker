package com.example.playlistmaker.search_activity.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.App
import com.example.playlistmaker.audio_player_activity.domain.models.TrackDto
import com.example.playlistmaker.audio_player_activity.ui.AudioPlayerActivity
import com.example.playlistmaker.search_activity.data.dto.SearchViewState
import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.databinding.ActivitySearchBinding


class SearchActivity : AppCompatActivity(), TrackListAdapter.ItemClickInterface,
    TrackListAdapterHistory.ItemClickInterfaceHistory {

    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(
            this,
            SearchViewModel.getViewModelFactory(applicationContext)
        )[SearchViewModel::class.java]
    }

    private var trackListAdapter: TrackListAdapter? = null
    private var trackListAdapterHistory: TrackListAdapterHistory? = null

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())


    companion object {
        private const val teg = "SearchActivity"
        private const val key: String = "key"
        private val trackList = arrayListOf<TrackDtoApp>()
        private val trackListHistory=arrayListOf<TrackDtoApp>()
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        trackListAdapter = TrackListAdapter()
        trackListAdapter?.setInItemClickListener(this)
        binding.recyclerViewTrackList.adapter = trackListAdapter

        trackListAdapterHistory = TrackListAdapterHistory()
        trackListAdapterHistory?.setInItemClickListener(this)

        observeViewModelState()

        binding.recyclerViewTrackListHistory.adapter = trackListAdapterHistory

        binding.buttonCleanHistory.setOnClickListener {
            searchViewModel.removeTrackListInSharedPreferences()
            binding.layoutSearchHistory.visibility = View.GONE

        }

        binding.imageViewClear.setOnClickListener {
            binding.editTextSearch.setText("")

            binding.layoutEmptyResult.visibility = View.GONE
            binding.layoutNoInternetConnection.visibility = View.GONE

            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(binding.editTextSearch.windowToken, 0)

        }

        binding.imageViewBackArrow.setOnClickListener { finish() }

        binding.buttonRefresh.setOnClickListener { searchTrack(binding.editTextSearch.text.toString()) }

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.imageViewClear.visibility = View.GONE
                    trackListAdapter?.setTrackList(arrayListOf())
                } else {

                    searchViewModel.setTextTrack(s.toString())


                    binding.imageViewClear.visibility = View.VISIBLE
                }


                if (binding.editTextSearch.hasFocus() && s?.isEmpty() == true) {
                    Log.d(teg, "TextChangedListener focus - true")

                    if (trackListHistory.size != 0) {
                        binding.layoutSearchHistory.visibility = View.VISIBLE
                        binding.layoutRecyclerView.visibility = View.GONE
                    }else{
                        Log.d(teg, "trackListHistory.itemCount == 0")
                    }

                } else {
                    Log.d(teg, "TextChangedListener focus - false")
                    binding.layoutSearchHistory.visibility = View.GONE
                    binding.layoutRecyclerView.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.editTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                true
            }
            false
        }

        binding.editTextSearch.setOnFocusChangeListener { view, hasFocus ->

            if (hasFocus && binding.editTextSearch.text?.isEmpty() == true) {
                Log.d(teg, "focus - true")
                if (trackListAdapterHistory?.itemCount != 0) {
                    binding.layoutSearchHistory.visibility = View.VISIBLE
                    binding.layoutRecyclerView.visibility = View.GONE
                }

            } else {
                Log.d(teg, "focus - false")

            }
        }
    }

    private fun searchTrack(text: String) {
        searchViewModel.setTextTrack(text)
        searchViewModel.searchDebounce()
    }

    private fun observeViewModelState() {
        searchViewModel.searchViewModelState.observe(this) {
            when (it) {
                is SearchViewState.Loading ->
                    showStartNewRequestLoading()

                is SearchViewState.Error -> showErrorResult()
                is SearchViewState.Empty -> showEmptyResult()
                is SearchViewState.SearchViewStateData -> {
                    trackList.clear()
                    trackList.addAll(it.trackList)
                    trackListAdapter?.setTrackList(trackList)
                    showSuccessfulResult()
                }

                is SearchViewState.SearchViewStateDataHistory -> {
                    trackListHistory.clear()
                    trackListHistory.addAll(it.trackListHistory)
                    trackListAdapterHistory?.setTrackList(trackListHistory)
                }
            }

        }

    }

    private fun showSuccessfulResult() {
        binding.layoutEmptyResult.visibility = View.GONE
        binding.layoutNoInternetConnection.visibility = View.GONE
        binding.layoutRecyclerView.visibility = View.VISIBLE
        binding.frameLayoutProgressbar.visibility = View.GONE
    }

    private fun showStartNewRequestLoading() {
        binding.layoutEmptyResult.visibility = View.GONE
        binding.layoutNoInternetConnection.visibility = View.GONE
        binding.layoutRecyclerView.visibility = View.GONE
        binding.frameLayoutProgressbar.visibility = View.VISIBLE
    }

    private fun showEmptyResult() {
        binding.layoutEmptyResult.visibility = View.VISIBLE
        binding.layoutNoInternetConnection.visibility = View.GONE
        binding.layoutRecyclerView.visibility = View.GONE
        binding.frameLayoutProgressbar.visibility = View.GONE
    }

    private fun showErrorResult() {
        binding.layoutNoInternetConnection.visibility = View.VISIBLE
        binding.layoutEmptyResult.visibility = View.GONE
        binding.layoutRecyclerView.visibility = View.GONE
        binding.frameLayoutProgressbar.visibility = View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString(key, binding.editTextSearch.text.toString())
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        binding.editTextSearch.setText(savedInstanceState?.getString(key))
    }

    override fun onItemClick(track: TrackDtoApp) {
        if (clickDebounce()) {
            searchViewModel.writeOneTrack(track)
            goToActivity(track)
        }
    }

    override fun onItemClickHistory(track: TrackDtoApp) {
        if (clickDebounce()) {
            goToActivity(track)
        }
    }

    private fun goToActivity(track: TrackDtoApp) {
        val intent = Intent(
            this,
            AudioPlayerActivity::class.java
        )

        intent.putExtra(
            App.trackKey, TrackDto(
                track.trackId,
                track.trackName,
                track.artistName,
                track.trackTimeMillis,
                track.artworkUrl100,
                track.collectionName,
                track.releaseDate,
                track.primaryGenreName,
                track.country,
                track.previewUrl
            )
        )

        startActivity(intent)
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY_MILLIS)
        }
        return current
    }

}