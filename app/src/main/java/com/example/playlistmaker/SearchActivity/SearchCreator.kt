package com.example.playlistmaker.SearchActivity

import android.content.Context
import com.example.playlistmaker.SearchActivity.data.SearchHistoryRepositoryImpl
import com.example.playlistmaker.SearchActivity.data.SearchRepositoryImpl
import com.example.playlistmaker.SearchActivity.domain.api.SearchHistoryInteractorInterface
import com.example.playlistmaker.SearchActivity.domain.api.SearchHistoryRepositoryInterface
import com.example.playlistmaker.SearchActivity.domain.api.SearchInteractorInterface
import com.example.playlistmaker.SearchActivity.domain.api.SearchRepositoryInterface
import com.example.playlistmaker.SearchActivity.domain.impl.SearchHistoryInteractorImpl
import com.example.playlistmaker.SearchActivity.domain.impl.SearchInteractorImpl
import com.example.playlistmaker.SettingsActivity.ui.SettingsActivity
import com.example.playlistmaker.SearchActivity.data.network.ITunesSearchClient
import com.example.playlistmaker.SearchActivity.data.network.ITunesSearchInterface

object SearchCreator {
    fun getSearchInteractor(): SearchInteractorInterface {
        return SearchInteractorImpl(
            getSearchRepository()
        )
    }

    private fun getSearchRepository(): SearchRepositoryInterface {
        val apiITunesSearchClient =
            ITunesSearchClient.getApiRetrofitClient()?.create(ITunesSearchInterface::class.java)
        return SearchRepositoryImpl(apiITunesSearchClient)
    }


    fun getSearchHistoryInteractor(context: Context): SearchHistoryInteractorInterface {
        return SearchHistoryInteractorImpl(getSearchHistoryRepository(context))

    }

    private fun getSearchHistoryRepository(context: Context): SearchHistoryRepositoryInterface {
        return SearchHistoryRepositoryImpl(
            context.getSharedPreferences(
                SettingsActivity.PRACTICUM_EXAMPLE_PREFERENCES,
                Context.MODE_PRIVATE
            )
        )
    }


}