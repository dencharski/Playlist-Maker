package com.example.playlistmaker.mediateka.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.playlistmaker.databinding.FragmentPlaylistsBinding

class PlaylistsFragment : Fragment() {

    private var _binding: FragmentPlaylistsBinding? = null
    private val binding: FragmentPlaylistsBinding get() = _binding!!

    private val playListsViewModel: PlayListsViewModel by viewModels()

    private val tag = "fragment"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(tag, "${requireArguments().getString(PLAYLISTS_URL)}")
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val PLAYLISTS_URL = "playlists_url"

        fun newInstance(playlistsUrl: String) = PlaylistsFragment().apply {
            arguments = Bundle().apply {
                putString(PLAYLISTS_URL, playlistsUrl)
            }
        }
    }
}