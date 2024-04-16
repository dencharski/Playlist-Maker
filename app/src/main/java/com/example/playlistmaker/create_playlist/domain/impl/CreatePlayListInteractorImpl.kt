package com.example.playlistmaker.create_playlist.domain.impl

import com.example.playlistmaker.create_playlist.domain.api.CreatePlayListInteractor
import com.example.playlistmaker.create_playlist.domain.api.CreatePlayListRepository
import com.example.playlistmaker.create_playlist.domain.models.PlayList

class CreatePlayListInteractorImpl(
    private val createPlayListRepository: CreatePlayListRepository
) : CreatePlayListInteractor {
    override suspend fun createPlaylist(playList: PlayList) {
            createPlayListRepository.createPlaylist(playList)

    }
}