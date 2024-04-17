package com.example.playlistmaker.audio_player.domain.models

import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.mediateka.domain.models.PlayListsViewState

sealed class AudioPlayerViewState {

    object Play : AudioPlayerViewState()
    object Pause : AudioPlayerViewState()
    object PlayCompleted:AudioPlayerViewState()
    object Error : AudioPlayerViewState()
    data class CurrentPosition(val currentPosition: String = "00:00") : AudioPlayerViewState()
    data class AddFavoriteClick(var isFavorite: Boolean = false) : AudioPlayerViewState()
    object ListOfPlayListsIsEmpty : AudioPlayerViewState()
    data class ListOfPlayLists(val list:List<PlayList>) : AudioPlayerViewState()
    object PlayListContainTrack :AudioPlayerViewState()
    data class AddTrackInPlayList(val name:String):AudioPlayerViewState()
}
