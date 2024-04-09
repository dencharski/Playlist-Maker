package com.example.playlistmaker.mediateka.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.databinding.FragmentPlaylistsBinding
import com.example.playlistmaker.mediateka.domain.models.PlayListsViewState
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlaylistsFragment : Fragment(), PlayListsAdapter.ItemClickInterface {

    private var _binding: FragmentPlaylistsBinding? = null
    private val binding: FragmentPlaylistsBinding get() = _binding!!

    private val playListsViewModel: PlayListsViewModel by viewModel()
    private var playListsAdapter: PlayListsAdapter? = null

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

        binding.buttonNewPlaylist.setOnClickListener {
            findNavController().navigate(R.id.action_mediatekaFragment_to_createPlaylistFragment)
        }

        playListsAdapter = PlayListsAdapter()
        playListsAdapter?.setInItemClickListener(this)
        binding.recyclerViewPlaylist.adapter = playListsAdapter

        observeViewState()
        Log.d(tag, "${requireArguments().getString(PLAYLISTS_URL)}")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag,"onResume()")
        playListsViewModel.getListOfPlayLists()
    }

    private fun observeViewState() {
        playListsViewModel.playListsViewState.observe(viewLifecycleOwner) {
            when (it) {
                is PlayListsViewState.ListOfPlayListsIsEmpty -> {
                    setViewIsEmpty()
                }
                is PlayListsViewState.ListOfPlayLists -> {
                    playListsAdapter?.setListOfPlayLists(it.list)
                    setViewWithData()
                }
                else -> {}
            }
        }
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

    override fun onItemClick(playList: PlayList) {
        Log.d(tag, "playlistClick ")
    }
    private fun setViewIsEmpty(){
        binding.layoutRecyclerView.visibility=View.GONE
        binding.imageViewEmptyResult.visibility=View.VISIBLE
        binding.textView.visibility=View.VISIBLE
    }
    private fun setViewWithData(){
        binding.layoutRecyclerView.visibility=View.VISIBLE
        binding.imageViewEmptyResult.visibility=View.GONE
        binding.textView.visibility=View.GONE
    }
}