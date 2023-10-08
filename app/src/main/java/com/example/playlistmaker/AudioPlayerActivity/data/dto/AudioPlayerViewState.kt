package com.example.playlistmaker.AudioPlayerActivity.data.dto

import android.media.MediaPlayer
import com.example.playlistmaker.TrackDtoApp

data class AudioPlayerViewState(
    var track: TrackDtoApp?,
    var mediaPlayer: MediaPlayer
)