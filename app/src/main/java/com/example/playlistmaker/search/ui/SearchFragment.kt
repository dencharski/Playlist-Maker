package com.example.playlistmaker.search.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.playlistmaker.App
import com.example.playlistmaker.TrackDtoApp
import com.example.playlistmaker.audio_player.data.dto.TrackDto
import com.example.playlistmaker.audio_player.ui.AudioPlayerActivity
import com.example.playlistmaker.databinding.FragmentSearchBinding
import com.example.playlistmaker.search.domain.models.SearchViewState
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : Fragment(), TrackListAdapter.ItemClickInterface,
    TrackListAdapterHistory.ItemClickInterfaceHistory {

    private var _binding: FragmentSearchBinding? = null
    private val binding:FragmentSearchBinding get() = _binding!!

    private val searchViewModel by viewModel<SearchViewModel>()

    private var trackListAdapter: TrackListAdapter? = null
    private var trackListAdapterHistory: TrackListAdapterHistory? = null

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trackListAdapter = TrackListAdapter()
        trackListAdapter?.setInItemClickListener(this)
        binding.recyclerViewTrackList.adapter = trackListAdapter

        trackListAdapterHistory = TrackListAdapterHistory()
        trackListAdapterHistory?.setInItemClickListener(this)
        trackListAdapterHistory?.setTrackList(trackListHistory)

        observeViewModelState()

        binding.recyclerViewTrackListHistory?.adapter = trackListAdapterHistory

        binding?.buttonCleanHistory?.setOnClickListener {
            searchViewModel.removeTrackListInSharedPreferences()
            binding?.layoutSearchHistory?.visibility = View.GONE
        }

        savedInstanceState?.let {
            binding?.editTextSearch?.setText(it.getString(key))
        }

        binding?.imageViewClear?.setOnClickListener {
            binding?.editTextSearch?.setText("")

            binding?.layoutEmptyResult?.visibility = View.GONE
            binding?.layoutNoInternetConnection?.visibility = View.GONE


            val inputMethodManager =
                requireActivity().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(binding?.editTextSearch?.windowToken, 0)
        }



        binding?.buttonRefresh?.setOnClickListener { searchTrack(binding?.editTextSearch?.text.toString()) }

        binding?.editTextSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding?.imageViewClear?.visibility = View.GONE
                    trackListAdapter?.setTrackList(arrayListOf())
                } else {
                    searchViewModel.setTextTrack(s.toString())
                    binding?.imageViewClear?.visibility = View.VISIBLE
                }

                if (binding?.editTextSearch?.hasFocus() == true && s?.isEmpty() == true) {
                    Log.d(tag, "TextChangedListener focus - true")
                    if (trackListHistory.size != 0) {
                        binding?.layoutSearchHistory?.visibility = View.VISIBLE
                        binding?.layoutRecyclerView?.visibility = View.GONE
                    } else {
                        Log.d(tag, "trackListHistory.itemCount == 0")
                    }
                } else {
                    Log.d(tag, "TextChangedListener focus - false")
                    binding?.layoutSearchHistory?.visibility = View.GONE
                    binding?.layoutRecyclerView?.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding?.editTextSearch?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                true
            }
            false
        }

        binding?.editTextSearch?.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus && binding?.editTextSearch?.text?.isEmpty() == true) {
                Log.d(tag, "focus - true")
                if (trackListAdapterHistory?.itemCount != 0) {
                    binding?.layoutSearchHistory?.visibility = View.VISIBLE
                    binding?.layoutRecyclerView?.visibility = View.GONE
                }
            } else {
                Log.d(tag, "focus - false")
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun searchTrack(text: String) {
        searchViewModel.setTextTrack(text)
        searchViewModel.searchDebounce()
    }

    private fun observeViewModelState() {
        searchViewModel.searchViewModelState.observe(viewLifecycleOwner) {
            when (it) {
                is SearchViewState.Loading -> showStartNewRequestLoading()
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
        binding?.layoutEmptyResult?.visibility = View.GONE
        binding?.layoutNoInternetConnection?.visibility = View.GONE
        binding?.layoutRecyclerView?.visibility = View.VISIBLE
        binding?.frameLayoutProgressbar?.visibility = View.GONE
    }

    private fun showStartNewRequestLoading() {
        binding?.layoutEmptyResult?.visibility = View.GONE
        binding?.layoutNoInternetConnection?.visibility = View.GONE
        binding?.layoutRecyclerView?.visibility = View.GONE
        binding?.frameLayoutProgressbar?.visibility = View.VISIBLE
    }

    private fun showEmptyResult() {
        binding?.layoutEmptyResult?.visibility = View.VISIBLE
        binding?.layoutNoInternetConnection?.visibility = View.GONE
        binding?.layoutRecyclerView?.visibility = View.GONE
        binding?.frameLayoutProgressbar?.visibility = View.GONE
    }

    private fun showErrorResult() {
        binding?.layoutNoInternetConnection?.visibility = View.VISIBLE
        binding?.layoutEmptyResult?.visibility = View.GONE
        binding?.layoutRecyclerView?.visibility = View.GONE
        binding?.frameLayoutProgressbar?.visibility = View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(key, binding?.editTextSearch?.text.toString())
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
            parentFragment?.activity,
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

    companion object {
        private const val tag = "SearchActivity"
        private const val key: String = "key"
        private val trackList = arrayListOf<TrackDtoApp>()
        private val trackListHistory = arrayListOf<TrackDtoApp>()
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }

}