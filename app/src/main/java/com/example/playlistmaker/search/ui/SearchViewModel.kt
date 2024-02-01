package com.example.playlistmaker.search.ui


import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.search.domain.models.ResponseModel
import com.example.playlistmaker.search.domain.models.SearchViewState
import com.example.playlistmaker.search.domain.api.SearchHistoryInteractor
import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.TrackDtoApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
    private val searchHistoryInteractor: SearchHistoryInteractor
) : ViewModel() {
    private var textTrack: String = ""
    private val _searchViewModelState = MutableLiveData<SearchViewState>()
    val searchViewModelState: LiveData<SearchViewState> get() = _searchViewModelState
    private var searchJob: Job? = null

    init {
        Log.d(teg, "init")
        getHistoryTrackList()
    }


    fun writeOneTrack(track: TrackDtoApp) {
        searchHistoryInteractor.writeOneTrack(track)
        getHistoryTrackList()
    }

    private fun getHistoryTrackList() {
        _searchViewModelState.postValue(SearchViewState.SearchViewStateDataHistory(trackListHistory = searchHistoryInteractor.getTrackList()))
    }

    fun removeTrackListInSharedPreferences() {
        searchHistoryInteractor.removeTrackListInSharedPreferences()
        getHistoryTrackList()
    }

    private fun searchTrack() {
        _searchViewModelState.postValue(SearchViewState.Loading)

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY_MILLIS)
            searchInteractor.searchTrack(textTrack)
                .collect { response ->
                    if (response != null) {
                        if (response.results.size != 0) {
                            _searchViewModelState.postValue(response.results.let {
                                SearchViewState.SearchViewStateData(
                                    trackList = it
                                )
                            })
                        } else {
                            _searchViewModelState.postValue(SearchViewState.Empty)
                        }

                    } else {
                        _searchViewModelState.postValue(SearchViewState.Error)
                    }
                }
        }


    }

    fun setTextTrack(text: String) {
        textTrack = text
        searchTrack()

    }

    override fun onCleared() {
        searchJob?.cancel()
    }

    companion object {
        private const val teg = "SearchActivity"
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
    }
}