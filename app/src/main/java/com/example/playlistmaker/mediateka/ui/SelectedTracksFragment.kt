package com.example.playlistmaker.mediateka.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.App
import com.example.playlistmaker.R
import com.example.playlistmaker.main.domain.models.TrackDtoApp
import com.example.playlistmaker.audio_player.ui.AudioPlayerFragment
import com.example.playlistmaker.databinding.FragmentSelectedTracksBinding
import com.example.playlistmaker.mediateka.domain.models.SelectedTracksViewState
import com.example.playlistmaker.search.ui.TrackListAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectedTracksFragment : Fragment(), SelectedTrackListAdapter.ItemClickInterface {

    private var _binding: FragmentSelectedTracksBinding? = null
    private val binding: FragmentSelectedTracksBinding get() = _binding!!

    private val selectedTracksViewModel by viewModel<SelectedTracksViewModel>()

    private var selectedTrackListAdapter: SelectedTrackListAdapter? = null

    private var isClickAllowed = true

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
        Log.d(tag,"prepareViews() SelectedTracksFragment")
        selectedTrackListAdapter = SelectedTrackListAdapter()
        selectedTrackListAdapter?.setInItemClickListener(this)
        binding.recyclerViewTrackList.adapter = selectedTrackListAdapter

    }

    override fun onResume() {
        super.onResume()
        isClickAllowed=true
        selectedTracksViewModel.getSelectedTracks()

    }

    override fun onDestroyView() {
        super.onDestroyView()
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

                else -> {}
            }
        }

    }


    override fun onItemClick(track: TrackDtoApp) {
        if (clickDebounce()) {
            goToAudioPlayerFragment(track)
        }
    }

    private fun goToAudioPlayerFragment(track: TrackDtoApp) {
        val bundle = Bundle()
        bundle.putParcelable(App.trackKey, track)

        findNavController().navigate(
            R.id.action_mediatekaFragment_to_audioPlayerFragment,bundle
        )
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