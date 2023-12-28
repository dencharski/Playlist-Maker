package com.example.playlistmaker.mediateka.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentMediatekaBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MediatekaFragment : Fragment() {

    private var _binding: FragmentMediatekaBinding? = null
    private val binding: FragmentMediatekaBinding get() = _binding!!

    private val mediatekaViewModel by viewModel<MediatekaViewModel>()

    private var tabMediator: TabLayoutMediator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediatekaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pager.adapter = PagerAdapter(
            childFragmentManager,
            lifecycle
        )

        tabMediator = TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getText(R.string.selected_tracks)
                1 -> tab.text = resources.getText(R.string.playlists)
            }
        }
        tabMediator?.attach()
    }


    override fun onDestroy() {
        super.onDestroy()
        tabMediator?.detach()
        _binding = null
    }
}