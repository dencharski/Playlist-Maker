package com.example.playlistmaker.di

import android.content.Context
import android.media.MediaPlayer
import androidx.room.Room
import com.example.playlistmaker.audio_player.data.AudioPlayerFavoriteTrackRepositoryImpl
import com.example.playlistmaker.audio_player.data.AudioPlayerRepositoryImpl
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerFavoriteTrackInteractor
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerFavoriteTrackRepository
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerInteractor
import com.example.playlistmaker.audio_player.domain.api.AudioPlayerRepository
import com.example.playlistmaker.audio_player.domain.impl.AudioPlayerFavoriteTrackInteractorImpl
import com.example.playlistmaker.audio_player.domain.impl.AudioPlayerInteractorImpl
import com.example.playlistmaker.audio_player.ui.AudioPlayerViewModel
import com.example.playlistmaker.main.data.MainRepositoryImpl
import com.example.playlistmaker.main.domain.MainInteractorImpl
import com.example.playlistmaker.main.domain.api.MainInteractor
import com.example.playlistmaker.main.domain.api.MainRepository
import com.example.playlistmaker.main.ui.MainViewModel
import com.example.playlistmaker.mediateka.data.SelectedTracksRepositoryImpl
import com.example.playlistmaker.mediateka.data.TrackDbConvertor
import com.example.playlistmaker.mediateka.data.db.TracksDatabase
import com.example.playlistmaker.mediateka.domain.api.SelectedTrackInteractor
import com.example.playlistmaker.mediateka.domain.api.SelectedTracksRepository
import com.example.playlistmaker.mediateka.domain.impl.SelectedTrackInteractorImpl
import com.example.playlistmaker.mediateka.ui.MediatekaViewModel
import com.example.playlistmaker.mediateka.ui.PlayListsViewModel
import com.example.playlistmaker.mediateka.ui.SelectedTracksViewModel
import com.example.playlistmaker.search.data.SearchHistoryRepositoryImpl
import com.example.playlistmaker.search.data.SearchRepositoryImpl
import com.example.playlistmaker.search.data.TrackHistoryConvertor
import com.example.playlistmaker.search.data.network.ITunesSearchInterface
import com.example.playlistmaker.search.domain.api.SearchHistoryInteractor
import com.example.playlistmaker.search.domain.api.SearchHistoryRepository
import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.search.domain.api.SearchRepository
import com.example.playlistmaker.search.domain.impl.SearchHistoryInteractorImpl
import com.example.playlistmaker.search.domain.impl.SearchInteractorImpl
import com.example.playlistmaker.search.ui.SearchViewModel
import com.example.playlistmaker.settings.data.SettingsRepositoryImpl
import com.example.playlistmaker.settings.domain.api.SettingsInteractor
import com.example.playlistmaker.settings.domain.api.SettingsRepository
import com.example.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import com.example.playlistmaker.settings.ui.SettingsFragment
import com.example.playlistmaker.settings.ui.SettingsViewModel
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

        single {
            Room.databaseBuilder(androidContext(), TracksDatabase::class.java, "database.db")
                .build()
        }

    }
    val repositoryModule = module {
        single<AudioPlayerRepository> { AudioPlayerRepositoryImpl(get()) }
        single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
        single<SearchHistoryRepository> { SearchHistoryRepositoryImpl(get(), get()) }
        single<SettingsRepository> { SettingsRepositoryImpl(get()) }
        single<MainRepository> { MainRepositoryImpl(get()) }
        single<SelectedTracksRepository> { SelectedTracksRepositoryImpl(get()) }

        factory { TrackDbConvertor() }
        factory { TrackHistoryConvertor() }

        single<AudioPlayerFavoriteTrackRepository> {
            AudioPlayerFavoriteTrackRepositoryImpl(
                get()
            )
        }
    }
    val interactorModule = module {
        single<AudioPlayerInteractor> { AudioPlayerInteractorImpl(get()) }
        single<SearchInteractor> { SearchInteractorImpl(get()) }
        single<SearchHistoryInteractor> { SearchHistoryInteractorImpl(get(), get()) }
        single<SettingsInteractor> { SettingsInteractorImpl(get()) }
        single<MainInteractor> { MainInteractorImpl(get()) }
        single<AudioPlayerFavoriteTrackInteractor> {
            AudioPlayerFavoriteTrackInteractorImpl(
                get(),
                get()
            )
        }
        single<SelectedTrackInteractor> { SelectedTrackInteractorImpl(get(), get()) }
    }
    val viewModelModule = module {
        viewModel { AudioPlayerViewModel(get(), get()) }
        viewModel { MainViewModel(get()) }
        viewModel { MediatekaViewModel() }
        viewModel { SearchViewModel(get(), get()) }
        viewModel { SettingsViewModel(get()) }
        viewModel { SelectedTracksViewModel(get()) }
        viewModel { PlayListsViewModel() }
    }
}