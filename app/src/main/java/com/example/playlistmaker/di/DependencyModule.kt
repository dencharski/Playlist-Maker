package com.example.playlistmaker.di

import android.content.Context
import android.media.MediaPlayer
import com.example.playlistmaker.audio_player_activity.data.AudioPlayerRepositoryImpl
import com.example.playlistmaker.audio_player_activity.domain.api.AudioPlayerInteractor
import com.example.playlistmaker.audio_player_activity.domain.api.AudioPlayerRepository
import com.example.playlistmaker.audio_player_activity.domain.impl.AudioPlayerInteractorImpl
import com.example.playlistmaker.audio_player_activity.ui.AudioPlayerViewModel
import com.example.playlistmaker.main_activity.data.MainRepositoryImpl
import com.example.playlistmaker.main_activity.domain.MainInteractorImpl
import com.example.playlistmaker.main_activity.domain.api.MainInteractor
import com.example.playlistmaker.main_activity.domain.api.MainRepository
import com.example.playlistmaker.main_activity.ui.MainViewModel
import com.example.playlistmaker.mediateka_fragment.ui.MediatekaViewModel
import com.example.playlistmaker.mediateka_fragment.ui.PlayListsViewModel
import com.example.playlistmaker.mediateka_fragment.ui.SelectedTracksViewModel
import com.example.playlistmaker.search_fragment.data.SearchHistoryRepositoryImpl
import com.example.playlistmaker.search_fragment.data.SearchRepositoryImpl
import com.example.playlistmaker.search_fragment.data.network.ITunesSearchInterface
import com.example.playlistmaker.search_fragment.domain.api.SearchHistoryInteractor
import com.example.playlistmaker.search_fragment.domain.api.SearchHistoryRepository
import com.example.playlistmaker.search_fragment.domain.api.SearchInteractor
import com.example.playlistmaker.search_fragment.domain.api.SearchRepository
import com.example.playlistmaker.search_fragment.domain.impl.SearchHistoryInteractorImpl
import com.example.playlistmaker.search_fragment.domain.impl.SearchInteractorImpl
import com.example.playlistmaker.search_fragment.ui.SearchViewModel
import com.example.playlistmaker.settings_fragment.data.SettingsRepositoryImpl
import com.example.playlistmaker.settings_fragment.domain.api.SettingsInteractor
import com.example.playlistmaker.settings_fragment.domain.api.SettingsRepository
import com.example.playlistmaker.settings_fragment.domain.impl.SettingsInteractorImpl
import com.example.playlistmaker.settings_fragment.ui.SettingsFragment
import com.example.playlistmaker.settings_fragment.ui.SettingsViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DependencyModule {

    val dataModule = module {
        single<ITunesSearchInterface> {
            Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .client(
                    OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ITunesSearchInterface::class.java)
        }

        single {
            androidContext()
                .getSharedPreferences(
                    SettingsFragment.PRACTICUM_EXAMPLE_PREFERENCES,
                    Context.MODE_PRIVATE
                )
        }

        single { MediaPlayer() }

    }
    val repositoryModule = module {
        single<AudioPlayerRepository> { AudioPlayerRepositoryImpl(get()) }
        single<SearchRepository> { SearchRepositoryImpl(get()) }
        single<SearchHistoryRepository> { SearchHistoryRepositoryImpl(get()) }
        single<SettingsRepository> { SettingsRepositoryImpl(get()) }
        single<MainRepository> { MainRepositoryImpl(get()) }
    }
    val interactorModule = module {
        single<AudioPlayerInteractor> { AudioPlayerInteractorImpl(get()) }
        single<SearchInteractor> { SearchInteractorImpl(get()) }
        single<SearchHistoryInteractor> { SearchHistoryInteractorImpl(get()) }
        single<SettingsInteractor> { SettingsInteractorImpl(get()) }
        single<MainInteractor> { MainInteractorImpl(get()) }
    }
    val viewModelModule = module {
        viewModel { AudioPlayerViewModel(get()) }
        viewModel { MainViewModel(get()) }
        viewModel { MediatekaViewModel() }
        viewModel { SearchViewModel(get(), get()) }
        viewModel { SettingsViewModel(get()) }
        viewModel { SelectedTracksViewModel() }
        viewModel { PlayListsViewModel() }
    }
}