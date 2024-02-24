package com.example.playlistmaker.mediateka.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.playlistmaker.App
import com.example.playlistmaker.main.domain.models.TrackDtoApp
import com.example.playlistmaker.audio_player.ui.AudioPlayerActivity
import com.example.playlistmaker.databinding.FragmentSelectedTracksBinding
import com.example.playlistmaker.mediateka.domain.models.SelectedTracksViewState
import com.example.playlistmaker.search.ui.TrackListAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectedTracksFragment : Fragment(), TrackListAdapter.ItemClickInterface {

    private var _binding: FragmentSelectedTracksBinding? = null
    private val binding: FragmentSelectedTracksBinding get() = _binding!!

    private val selectedTracksViewModel by viewModel<SelectedTracksViewModel>()

    private var selectedTrackListAdapter: TrackListAdapter? = null

    private var isClickAllowed = true

    private val tag = "fragment"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectedTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tag, "${requireArguments().getString(SELECTED_TRACKS_URL)}")


        prepareViews()
        observeValues()
    }

    private fun prepareViews() {
        selectedTrackListAdapter = TrackListAdapter()
        selectedTrackListAdapter?.setInItemClickListener(this)
        binding.recyclerViewTrackList.adapter = selectedTrackListAdapter

    }

    override fun onResume() {
        super.onResume()

        selectedTracksViewModel.getSelectedTracks()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observeValues() {

        selectedTracksViewModel.selectedTracksViewState.observe(viewLifecycleOwner) {
            when (it) {
                is SelectedTracksViewState.ListSelectedTracksEmpty -> {
                    binding.imageViewEmptyResult.visibility = View.VISIBLE
                    binding.textView.visibility = View.VISIBLE
                    binding.layoutRecyclerView.visibility = View.INVISIBLE
                }

                is SelectedTracksViewState.ListSelectedTracks -> {
                    selectedTrackListAdapter?.setTrackList(it.list as ArrayList<TrackDtoApp>)
                    binding.imageViewEmptyResult.visibility = View.INVISIBLE
                    binding.textView.visibility = View.INVISIBLE
                    binding.layoutRecyclerView.visibility = View.VISIBLE
                }

            }
        }

    }


    override fun onItemClick(track: TrackDtoApp) {
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
            App.trackKey,track
        )
        startActivity(intent)
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY_MILLIS)
                isClickAllowed = true
            }
        }
        return current
    }

    companion object {
        private const val SELECTED_TRACKS_URL = "selected_tracks_url"
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L

        fun newInstance(tracksUrl: String) = SelectedTracksFragment().apply {
            arguments = Bundle().apply {
                putString(SELECTED_TRACKS_URL, tracksUrl)
            }
        }
    }
}