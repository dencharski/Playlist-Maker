package com.example.playlistmaker.search_activity

import android.content.Context
import com.example.playlistmaker.search_activity.data.SearchHistoryRepositoryImpl
import com.example.playlistmaker.search_activity.data.SearchRepositoryImpl
import com.example.playlistmaker.search_activity.domain.api.SearchHistoryInteractor
import com.example.playlistmaker.search_activity.domain.api.SearchHistoryRepository
import com.example.playlistmaker.search_activity.domain.api.SearchInteractor
import com.example.playlistmaker.search_activity.domain.api.SearchRepository
import com.example.playlistmaker.search_activity.domain.impl.SearchHistoryInteractorImpl
import com.example.playlistmaker.search_activity.domain.impl.SearchInteractorImpl
import com.example.playlistmaker.settings_activity.ui.SettingsActivity
import com.example.playlistmaker.search_activity.data.network.ITunesSearchClient
import com.example.playlistmaker.search_activity.data.network.ITunesSearchInterface

object SearchCreator {
    fun getSearchInteractor(): SearchInteractor {
        return SearchInteractorImpl(
            getSearchRepository()
        )
    }

    private fun getSearchRepository(): SearchRepository {
        val apiITunesSearchClient =
            ITunesSearchClient.getApiRetrofitClient()?.create(ITunesSearchInterface::class.java)
        return SearchRepositoryImpl(apiITunesSearchClient)
    }


    fun getSearchHistoryInteractor(context: Context): SearchHistoryInteractor {
        return SearchHistoryInteractorImpl(getSearchHistoryRepository(context))

    }

    private fun getSearchHistoryRepository(context: Context): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(
            context.getSharedPreferences(
                SettingsActivity.PRACTICUM_EXAMPLE_PREFERENCES,
                Context.MODE_PRIVATE
            )
        )
    }


}