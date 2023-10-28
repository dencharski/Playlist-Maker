package com.example.playlistmaker.audio_player_activity.domain.models

import com.example.playlistmaker.TrackDtoApp

sealed class AudioPlayerViewState {

    object Play : AudioPlayerViewState()
    object Pause : AudioPlayerViewState()
    object PlayCompleted:AudioPlayerViewState()
    object Error : AudioPlayerViewState()
    data class Track(
        val track: TrackDtoApp = TrackDtoApp(
            0, "", "", "", "", "", "", "", "", ""
        )
    ) : AudioPlayerViewState()

    data class CurrentPosition(val currentPosition: String = "00:00") : AudioPlayerViewState()
}
