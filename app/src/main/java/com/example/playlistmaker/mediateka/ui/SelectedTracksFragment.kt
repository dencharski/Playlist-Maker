package com.example.playlistmaker.mediateka.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.playlistmaker.databinding.FragmentSelectedTracksBinding

class SelectedTracksFragment : Fragment() {
    private var _binding: FragmentSelectedTracksBinding? = null
    private val binding: FragmentSelectedTracksBinding get() = _binding!!

    private val selectedTracksViewModel: SelectedTracksViewModel by viewModels()

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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val SELECTED_TRACKS_URL = "selected_tracks_url"

        fun newInstance(tracksUrl: String) = SelectedTracksFragment().apply {
            arguments = Bundle().apply {
                putString(SELECTED_TRACKS_URL, tracksUrl)
            }
        }
    }
}