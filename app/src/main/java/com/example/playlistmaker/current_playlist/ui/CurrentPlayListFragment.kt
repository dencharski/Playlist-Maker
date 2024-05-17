package com.example.playlistmaker.current_playlist.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.playlistmaker.App
import com.example.playlistmaker.R
import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.current_playlist.domain.models.CurrentPlayListViewState
import com.example.playlistmaker.databinding.FragmentCurrentPlayListBinding
import com.example.playlistmaker.main.domain.models.TrackApp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Locale

class CurrentPlayListFragment : Fragment(), CurrentPlayListTrackAdapter.ItemClickInterface,
    CurrentPlayListTrackAdapter.ItemLongClickInterface {


    private var _binding: FragmentCurrentPlayListBinding? = null
    private val binding: FragmentCurrentPlayListBinding get() = _binding!!

    private val currentPlayListViewModel: CurrentPlayListViewModel by viewModel()
    private var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>? = null
    private var bottomSheetBehaviorMenu: BottomSheetBehavior<LinearLayout>? = null
    private var confirmDialogDeleteTrack: MaterialAlertDialogBuilder? = null
    private var confirmDialogDeletePlayList: MaterialAlertDialogBuilder? = null
    private var currentPlayListTrackAdapter: CurrentPlayListTrackAdapter? = null

    private var playList: PlayList? = null
    private var listOfTracks = listOf<TrackApp>()
    private var playListId: Long = -1
    private val tag: String = "fragment"
    private var trackId: Long = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrentPlayListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareViews()

        observeValues()
    }

    override fun onResume() {
        super.onResume()

        if (arguments?.getLong(App.PLAYLIST_ID) != null) {
            playListId = arguments?.getLong(App.PLAYLIST_ID)!!
            currentPlayListViewModel.getPlayList(playListId)
        }
    }

    private fun prepareViews() {

        binding.imageViewBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }

        currentPlayListTrackAdapter = CurrentPlayListTrackAdapter()
        currentPlayListTrackAdapter?.setInItemClickListener(this)
        currentPlayListTrackAdapter?.setInItemLongClickListener(this)
        binding.recyclerViewTracksInPlaylist.adapter = currentPlayListTrackAdapter

        confirmDialogDeleteTrack = context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.delete_track))
                .setMessage(getString(R.string.shure_delete_track))
                .setNeutralButton(getString(R.string.negative)) { dialog, which ->
                    binding.overlayMenu.visibility = View.GONE
                    trackId = 0
                }
                .setPositiveButton(getString(R.string.positive)) { dialog, which ->
                    binding.overlayMenu.visibility = View.GONE
                    if (trackId != 0.toLong() && playList != null) {
                        currentPlayListViewModel.deleteOneTrackInPlayList(playList!!, trackId)
                    }

                }
        }

        confirmDialogDeletePlayList = context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.delete_playlist))
                .setMessage(getString(R.string.you_wont_delete_playlist))
                .setNeutralButton(getString(R.string.negative)) { dialog, which ->

                    binding.overlayMenu.visibility = View.GONE
                }
                .setPositiveButton(getString(R.string.positive)) { dialog, which ->
                    binding.overlayMenu.visibility = View.GONE
                    if (playList != null) {
                        currentPlayListViewModel.checkListTrackIds(listOfTracks)
                        currentPlayListViewModel.deletePlayList(playList!!)
                        findNavController().navigateUp()
                    }

                }
        }

        binding.imageViewSharedButton.setOnClickListener {
            sharePlayList()
        }

        binding.textViewSharePlaylist.setOnClickListener {
            sharePlayList()
        }

        binding.textViewEditPlaylist.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(App.PLAYLIST, playList)

            findNavController().navigate(
                R.id.action_currentPlayListFragment_to_createPlaylistFragment, bundle
            )
        }

        binding.textViewDeletePlaylist.setOnClickListener {
            bottomSheetBehaviorMenu?.state = BottomSheetBehavior.STATE_HIDDEN
            binding.overlayMenu.visibility = View.VISIBLE
            confirmDialogDeletePlayList?.show()
        }

        binding.imageViewMenuButton.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            bottomSheetBehaviorMenu?.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        bottomSheetBehavior = BottomSheetBehavior.from(binding.standardBottomSheet).apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
        }

        bottomSheetBehavior?.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {

                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.overlay.visibility = View.GONE

                    }

                    else -> {

                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })


        bottomSheetBehaviorMenu = BottomSheetBehavior.from(binding.bottomSheetMenu).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        bottomSheetBehaviorMenu?.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {

                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.overlay.visibility = View.GONE
                    }

                    else -> {
                        binding.overlay.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    private fun sharePlayList() {
        if (playList?.listOfTrackIds?.isEmpty() == true) {
            Toast.makeText(
                this.requireContext(),
                getString(R.string.empty_tracklist_in_playlist),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val stringSharedPlayList = "${playList?.playListName} \n" +
                    "${playList?.playlistDescription} \n" +
                    "${App.setTextInPlaylistSize(listOfTracks.size)} \n" +
                    "${getStringTrackInfo(listOfTracks)} "


            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = getString(R.string.intent_type)
            intent.putExtra(
                Intent.EXTRA_TEXT,
                stringSharedPlayList
            )
            startActivity(intent)
        }
    }

    private fun observeValues() {
        currentPlayListViewModel.currentPlayListViewState.observe(viewLifecycleOwner) {
            when (it) {
                is CurrentPlayListViewState.EmptyPlayList -> {
                    Log.d(tag, "empty playlist ${it.error}")
                    Toast.makeText(activity,
                        getString(R.string.empty_tracklist_in_playlist),Toast.LENGTH_SHORT).show()
                    binding.linearLayoutPlaylistParams.visibility=View.INVISIBLE
                    currentPlayListTrackAdapter?.setTrackList(arrayListOf<TrackApp>())
                }

                is CurrentPlayListViewState.PlayListState -> {

                    playList = it.playList

                    Glide.with(this)
                        .load(it.playList.playlistImageUri)
                        .placeholder(R.drawable.placeholder)
                        .centerCrop()
                        .into(binding.imageViewArtworkUrl)

                    binding.textViewPlaylistName.text = it.playList.playListName
                    binding.textViewDescription.text = it.playList.playlistDescription

                    binding.included.textViewPlaylistName.text = it.playList.playListName
                    Glide.with(this)
                        .load(it.playList.playlistImageUri)
                        .placeholder(R.drawable.placeholder)
                        .centerCrop()
                        .into(binding.included.imageViewPlaylistImage)

                    currentPlayListViewModel.getPlayListTracks(it.playList.listOfTrackIds)


                }

                is CurrentPlayListViewState.ListOfTracks -> {


                    listOfTracks = it.listOfTracks
                    currentPlayListTrackAdapter?.setTrackList(ArrayList(listOfTracks))
                    bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
                    binding.textViewDurationTimeTracks.text =
                        setDurationSumTrack(it.listOfTracks)
                    binding.textViewDurationTracks.text =
                        App.setTextInPlaylistSize(it.listOfTracks.size)
                    binding.included.textViewPlaylistSize.text =
                        App.setTextInPlaylistSize(it.listOfTracks.size)

                    binding.linearLayoutPlaylistParams.visibility=View.VISIBLE

                }

                is CurrentPlayListViewState.DeleteTrackIdFromPlayList -> {
                    currentPlayListViewModel.getPlayList(playList?.playListId!!)
                }

                else -> {
                    Log.d(tag, "else -> smth wrong!")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getStringTrackInfo(list: List<TrackApp>): String {
        var stringTracksInfo = ""
        for (it in list.indices) {
            stringTracksInfo += "${it + 1}. ${list[it].artistName} - ${list[it].trackName} (${list[it].getTrackTime()}) \n"
        }

        return stringTracksInfo
    }

    private fun setDurationSumTrack(list: List<TrackApp>): String {

        var durationSum: Long = 0
        list.forEach { it ->
            if (it.trackTimeMillis.isNotEmpty()) {
                try {
                    durationSum += it.trackTimeMillis.toLongOrNull()!!
                } catch (e: Exception) {
                    durationSum += 0
                    Log.d(tag, "exeption: ${e.message}")
                }

            }
        }

        return SimpleDateFormat("mm", Locale.getDefault()).format(durationSum) + " мин."
    }

    override fun onItemClick(track: TrackApp) {
        val bundle = Bundle()
        bundle.putParcelable(App.TRACK_KEY, track)

        findNavController().navigate(
            R.id.action_currentPlayListFragment_to_audioPlayerFragment, bundle
        )
    }

    override fun onItemLongClick(track: TrackApp): Boolean {
        trackId = track.trackId
        binding.overlayMenu.visibility = View.VISIBLE
        confirmDialogDeleteTrack?.show()

        return true
    }

}