package com.example.playlistmaker.audio_player.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.App
import com.example.playlistmaker.R
import com.example.playlistmaker.main.domain.models.TrackApp
import com.example.playlistmaker.audio_player.domain.models.AudioPlayerViewState
import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.databinding.FragmentAudioplayerBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.viewModel


class AudioPlayerFragment : Fragment(), PlayListAudioPlayerAdapter.ItemClickPlayListInterface {

    private var _binding: FragmentAudioplayerBinding? = null
    private val binding: FragmentAudioplayerBinding get() = _binding!!

    private var playListAudioPlayerAdapter: PlayListAudioPlayerAdapter? = null

    private var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>? = null

    private val audioPlayerViewModel by viewModel<AudioPlayerViewModel>()

    private val tag: String = "audio"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAudioplayerBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val track: TrackApp? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(App.trackKey, TrackApp::class.java)
        } else {
            arguments?.getParcelable(App.trackKey)
        }
        audioPlayerViewModel.setDataExtrasTrack(track)

        playListAudioPlayerAdapter = PlayListAudioPlayerAdapter()
        playListAudioPlayerAdapter?.setInItemClickListener(this)
        binding.recyclerViewPlaylists.adapter = playListAudioPlayerAdapter

        observeValues()

        binding.imageButtonPlayTrack.isEnabled = false

        binding.imageViewBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.imageButtonPlayTrack.setOnClickListener {
            audioPlayerViewModel.playbackControl()
        }

        binding.imageButtonPauseTrack.setOnClickListener {
            audioPlayerViewModel.playbackControl()
        }

        binding.imageButtonLikeItCheck.setOnClickListener {

            audioPlayerViewModel.onFavoriteClicked()
        }

        binding.imageButtonLikeItNotCheck.setOnClickListener {

            audioPlayerViewModel.onFavoriteClicked()
        }

        bottomSheetBehavior = BottomSheetBehavior.from(binding.standardBottomSheet).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.imageButtonAddLibrary.setOnClickListener {

            audioPlayerViewModel.getListOfPlayLists()
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.buttonNewPlaylist.setOnClickListener {
            findNavController().navigate(R.id.action_audioPlayerFragment_to_createPlaylistFragment)
        }

        bottomSheetBehavior?.addBottomSheetCallback(object :
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

    private fun observeValues() {

        audioPlayerViewModel.trackApp.observe(viewLifecycleOwner) {
            if (it != null) {
                setViews(it)
                binding.imageButtonPlayTrack.isEnabled = true
                binding.imageButtonPlayTrack.visibility = View.VISIBLE
                binding.imageButtonPauseTrack.visibility = View.INVISIBLE
                binding.textViewTrackTimeNowPlay.text = ZERO_VAL
            }
        }

        audioPlayerViewModel.audioPlayerViewState.observe(viewLifecycleOwner) {
            when (it) {
                is AudioPlayerViewState.Error -> {
                    Log.d(tag, "error view state")
                }

                is AudioPlayerViewState.Play -> {
                    binding.imageButtonPlayTrack.visibility = View.INVISIBLE
                    binding.imageButtonPauseTrack.visibility = View.VISIBLE
                }

                is AudioPlayerViewState.Pause -> {
                    binding.imageButtonPlayTrack.visibility = View.VISIBLE
                    binding.imageButtonPauseTrack.visibility = View.INVISIBLE
                }


                is AudioPlayerViewState.CurrentPosition -> {
                    binding.textViewTrackTimeNowPlay.text = it.currentPosition
                }

                is AudioPlayerViewState.PlayCompleted -> {
                    audioPlayerViewModel.playbackControl()
                    binding.textViewTrackTimeNowPlay.text = ZERO_VAL
                }

                is AudioPlayerViewState.AddFavoriteClick -> {
                    if (it.isFavorite) {

                        binding.imageButtonLikeItCheck.visibility = View.VISIBLE
                        binding.imageButtonLikeItNotCheck.visibility = View.INVISIBLE
                    } else {

                        binding.imageButtonLikeItCheck.visibility = View.INVISIBLE
                        binding.imageButtonLikeItNotCheck.visibility = View.VISIBLE
                    }

                }

                is AudioPlayerViewState.ListOfPlayLists -> {
                    playListAudioPlayerAdapter?.setListOfPlayLists(it.list)
                }

                is AudioPlayerViewState.ListOfPlayListsIsEmpty -> {
                    Log.d(tag, "List of play lists is empty")
                }

                is AudioPlayerViewState.AddTrackInPlayList -> {
                    Toast.makeText(
                        context,
                        resources.getString(R.string.add_in_playlist_now) + it.name,
                        Toast.LENGTH_SHORT
                    ).show()
                    bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
                }

                is AudioPlayerViewState.PlayListContainTrack -> {
                    bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
                    Toast.makeText(
                        context,
                        resources.getString(R.string.track_was_add_in_playlist_before),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    Log.d(tag, "smth wrong")
                }
            }
        }
    }

    private fun getCoverArtwork(track: TrackApp) = track.artworkUrl512

    private fun setViews(track: TrackApp) {

        binding.imageViewArtworkUrl.let {
            Glide.with(this)
                .load(getCoverArtwork(track))
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .transform(RoundedCorners(dpToPx(cornerRadius)))
                .into(it)
        }

        binding.textViewTrackName.text = track.trackName
        binding.textViewArtistName.text = track.artistName
        binding.textViewTrackTime.text = track.getTrackTime()
        binding.textViewCollectionName.text = track.collectionName
        binding.textViewReleaseDate.text = track.getDateRelease()
        binding.textViewPrimaryGenreName.text = track.primaryGenreName
        binding.textViewCountry.text = track.country

        if (track.isFavorite) {

            binding.imageButtonLikeItCheck.visibility = View.VISIBLE
            binding.imageButtonLikeItNotCheck.visibility = View.INVISIBLE
        } else {

            binding.imageButtonLikeItCheck.visibility = View.INVISIBLE
            binding.imageButtonLikeItNotCheck.visibility = View.VISIBLE
        }


    }

    private fun dpToPx(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            activity?.applicationContext?.resources?.displayMetrics
        ).toInt()
    }

    override fun onPause() {
        super.onPause()
        audioPlayerViewModel.onPause()

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy()")
        audioPlayerViewModel.onDestroy()
        _binding = null
    }

    companion object {
        private const val cornerRadius: Float = 8f
        private const val ZERO_VAL = "00:00"
        private const val tag = "fragment"
    }

    override fun onItemClick(playList: PlayList) {

        audioPlayerViewModel.checkTrackInPlaylist(playList)

    }
}