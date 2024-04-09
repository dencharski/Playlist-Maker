package com.example.playlistmaker.create_playlist.domain.impl

import com.example.playlistmaker.create_playlist.data.CreatePlayListDbConvertor
import com.example.playlistmaker.create_playlist.domain.api.CreatePlayListInteractor
import com.example.playlistmaker.create_playlist.domain.api.CreatePlayListRepository
import com.example.playlistmaker.create_playlist.domain.models.PlayList
import com.example.playlistmaker.mediateka.domain.api.PlayListRepository

class CreatePlayListInteractorImpl(
    private val playListRepository: PlayListRepository,
    private val createPlayListDbConvertor: CreatePlayListDbConvertor
) : CreatePlayListInteractor {
    override suspend fun createPlaylist(playList: PlayList) {
            playListRepository.createPlaylist(
                playList = createPlayListDbConvertor.map(playList)
            )
    }
}