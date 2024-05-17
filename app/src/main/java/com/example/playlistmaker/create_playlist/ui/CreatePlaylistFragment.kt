package com.example.playlistmaker.create_playlist.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.App
import com.example.playlistmaker.R
import com.example.playlistmaker.audio_player.ui.AudioPlayerFragment
import com.example.playlistmaker.create_playlist.domain.models.CreatePlayListViewState
import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.databinding.FragmentCreatePlaylistBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date


class CreatePlaylistFragment : Fragment() {

    private var _binding: FragmentCreatePlaylistBinding? = null
    private val binding: FragmentCreatePlaylistBinding get() = _binding!!
    private val createPlayListViewModel: CreatePlaylistViewModel by viewModel()
    private val tag: String = "fragment"
    private var isButtonCreateAvailable: Boolean = false
    private var isUserChooseImage: Boolean = false
    private var isUserEditPlayList: Boolean = false
    private var confirmDialog: MaterialAlertDialogBuilder? = null
    private var playListPictureUri: String = ""
    private var uri: String = ""
    private var cornerRadius = 8F
    private var playList: PlayList? = null


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                choosePicture()
            } else {
                Log.d(tag, "Пользователь отказал в предоставлении разрешения")
            }
        }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            setPlayListImage(uri)
        }

    private fun setPlayListImage(uri: Uri?) {
        if (uri != null && uri.toString() != "") {

            binding.imageViewPlaylistImage.clipToOutline = true
            binding.imageViewPlaylistImage.setBackgroundResource(R.drawable.rectangle_no_color)
            binding.imageViewPlaylistImage.setImageURI(uri)
            binding.imageViewPlaylistImage.scaleType = ImageView.ScaleType.CENTER

            isUserChooseImage = true
            this.uri = uri.toString()

        } else {
            Log.d(tag, "изображение не выбрано")
            binding.imageViewPlaylistImage.clipToOutline = true
            binding.imageViewPlaylistImage.setBackgroundResource(R.drawable.rectangle_no_color)
            binding.imageViewPlaylistImage.setImageResource(R.drawable.placeholder)
            binding.imageViewPlaylistImage.scaleType = ImageView.ScaleType.CENTER_CROP
            this.uri = ""
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(App.PLAYLIST, PlayList::class.java)
        } else {
            arguments?.getParcelable(App.PLAYLIST)
        }

        if (playList != null) {
            isUserEditPlayList = true
            createPlayListViewModel.setPlayList(playList!!)

        }

        binding.imageViewBackArrow.setOnClickListener {
            checkEmptyFields()
        }

        binding.editTextPlaylistName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.textViewHintName.visibility = View.GONE
                    binding.editTextPlaylistName.setBackgroundResource(R.drawable.rectangle)
                    binding.buttonCreatePlaylist.setBackgroundResource(R.drawable.rectangle_button)
                    binding.buttonEditPlaylist.setBackgroundResource(R.drawable.rectangle_button)

                    isButtonCreateAvailable = false
                } else {
                    binding.textViewHintName.visibility = View.VISIBLE
                    binding.editTextPlaylistName.setBackgroundResource(R.drawable.rectangle_blue)
                    binding.buttonCreatePlaylist.setBackgroundResource(R.drawable.rectangle_button_blue)
                    binding.buttonEditPlaylist.setBackgroundResource(R.drawable.rectangle_button_blue)

                    isButtonCreateAvailable = true
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.editTextPlaylistName.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                binding.textViewHintName.visibility = View.VISIBLE
                binding.editTextPlaylistName.setBackgroundResource(R.drawable.rectangle_blue)
                if (binding.editTextPlaylistName.text.isNullOrEmpty()) {
                    binding.buttonCreatePlaylist.setBackgroundResource(R.drawable.rectangle_button)
                    binding.buttonEditPlaylist.setBackgroundResource(R.drawable.rectangle_button)

                    isButtonCreateAvailable = false
                } else {
                    binding.buttonCreatePlaylist.setBackgroundResource(R.drawable.rectangle_button_blue)
                    binding.buttonEditPlaylist.setBackgroundResource(R.drawable.rectangle_button_blue)

                    isButtonCreateAvailable = true
                }
            } else {
                if (binding.editTextPlaylistName.text.isNullOrEmpty()) {
                    binding.textViewHintName.visibility = View.GONE
                    binding.editTextPlaylistName.setBackgroundResource(R.drawable.rectangle)
                    binding.buttonCreatePlaylist.setBackgroundResource(R.drawable.rectangle_button)
                    binding.buttonEditPlaylist.setBackgroundResource(R.drawable.rectangle_button)

                    isButtonCreateAvailable = false
                } else {
                    binding.textViewHintName.visibility = View.VISIBLE
                    binding.editTextPlaylistName.setBackgroundResource(R.drawable.rectangle_blue)
                    binding.buttonCreatePlaylist.setBackgroundResource(R.drawable.rectangle_button_blue)
                    binding.buttonEditPlaylist.setBackgroundResource(R.drawable.rectangle_button_blue)

                    isButtonCreateAvailable = true
                }

            }
        }

        binding.editTextPlaylistDescription.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                binding.textViewHintDescription.visibility = View.VISIBLE
                binding.editTextPlaylistDescription.setBackgroundResource(R.drawable.rectangle_blue)
            } else {
                if (binding.editTextPlaylistDescription.text.isNullOrEmpty()) {
                    binding.textViewHintDescription.visibility = View.GONE
                    binding.editTextPlaylistDescription.setBackgroundResource(R.drawable.rectangle)
                } else {
                    binding.textViewHintDescription.visibility = View.VISIBLE
                    binding.editTextPlaylistDescription.setBackgroundResource(R.drawable.rectangle_blue)
                }

            }

        }

        binding.buttonCreatePlaylist.setOnClickListener {
            if (isButtonCreateAvailable) {

                if (uri.isNotEmpty()) {
                    saveImageToPrivateStorage(uri.toUri())
                }

                Toast.makeText(
                    context,
                    getString(R.string.playlist_was_created, binding.editTextPlaylistName.text),
                    Toast.LENGTH_SHORT
                ).show()

                createPlayListViewModel.createPlaylist(
                    name = binding.editTextPlaylistName.text.toString(),
                    description = binding.editTextPlaylistDescription.text.toString(),
                    imageUri = playListPictureUri
                )

                findNavController().navigateUp()
            }
        }

        binding.buttonEditPlaylist.setOnClickListener {
            if (isButtonCreateAvailable) {

                if (uri.isNotEmpty()) {
                    Log.d(tag, "edit playlistImageUri= $uri")
                    saveImageToPrivateStorage(uri.toUri())
                } else {
                    Log.d(tag, "edit playlistImageUri is empty = $uri")
                }

                if (playList != null) {
                    Log.d(tag, "set in playList ImageUri  = $playListPictureUri")
                    createPlayListViewModel.editPlaylist(
                        name = binding.editTextPlaylistName.text.toString(),
                        description = binding.editTextPlaylistDescription.text.toString(),
                        imageUri = playListPictureUri,
                        playList = playList!!
                    )
                }
                findNavController().navigateUp()
            }
        }

        binding.imageViewPlaylistImage.setOnClickListener {
            checkPermission()
        }

        confirmDialog = context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(resources.getString(R.string.complete_playlist_title_text))
                .setMessage(resources.getString(R.string.complete_playlist_message_text))
                .setNeutralButton(resources.getString(R.string.complete_playlist_netyralbutton_text)) { dialog, which ->
                    // ничего не делаем
                }
                .setPositiveButton(resources.getString(R.string.complete_playlist_positivebutton_text)) { dialog, which ->
                    findNavController().navigateUp()
                }
        }

        addBackPressedCallback()

        observeValues()
    }

    private fun observeValues() {
        createPlayListViewModel.createPlayListState.observe(viewLifecycleOwner) {
            when (it) {
                is CreatePlayListViewState.EditPlayList -> {

                    binding.editTextPlaylistName.setText(it.playList.playListName)
                    binding.editTextPlaylistDescription.setText(it.playList.playlistDescription)
                    Log.d(tag, "observe playlistImageUri= ${it.playList.playlistImageUri}")
                    setPlayListImage(it.playList.playlistImageUri.toUri())

                    binding.textViewNewPlaylist.text = getString(R.string.edit)
                    binding.buttonCreatePlaylist.visibility = View.GONE
                    binding.buttonEditPlaylist.visibility = View.VISIBLE
                }

                else -> {
                    Log.d(tag, "observe Values smth wrong")
                }
            }
        }
    }


    private fun saveImageToPrivateStorage(uri: Uri) {

        val filePath =
            File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myalbum")

        if (!filePath.exists()) {
            filePath.mkdirs()
        }

        val file =
            File(filePath, "${SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(Date())}_cover.jpg")

        val inputStream = context?.contentResolver?.openInputStream(uri)

        val outputStream = FileOutputStream(file)
        try {
            BitmapFactory
                .decodeStream(inputStream)
                .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
        } catch (e: Exception) {
            Log.d(tag, "saveImageToPrivateStorage catch ${e.localizedMessage}")
        }


        inputStream?.close()
        outputStream.close()

        Log.d(tag, "saveImageToPrivateStorage uri= ${Uri.fromFile(file).toString()}")
        playListPictureUri = Uri.fromFile(file).toString()
        Log.d(tag, "saveImageToPrivateStorage playListPictureUri= $playListPictureUri")

    }

    private fun addBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                try {
                    checkEmptyFields()
                } catch (e: Exception) {
                    Log.d(tag, "catch ${e.localizedMessage}")
                    findNavController().navigateUp()
                }

            }
        })
    }

    private fun checkEmptyFields() {
        if (isUserEditPlayList) {
            findNavController().navigateUp()

        } else {
            if (isFieldPictureNameAndDescriptionIsEmpty()) {
                findNavController().navigateUp()
            } else {
                confirmDialog?.show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun checkPermission() {
        val permissionProvided =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_MEDIA_IMAGES
                )
            } else {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        if (permissionProvided == PackageManager.PERMISSION_GRANTED) {
            Log.d(tag, "checkPermission true")

            choosePicture()

        } else if (permissionProvided == PackageManager.PERMISSION_DENIED) {
            Log.d(tag, "checkPermission false")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }


    private fun choosePicture() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun isFieldPictureNameAndDescriptionIsEmpty(): Boolean {
        var isEmpty = true
        if (binding.editTextPlaylistName.text.toString().isNotEmpty()) {
            isEmpty = false

        }
        if (binding.editTextPlaylistDescription.text.toString().isNotEmpty()) {
            isEmpty = false

        }
        if (isUserChooseImage) {
            isEmpty = false

        }

        return isEmpty
    }

}