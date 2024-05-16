package com.example.playlistmaker.search.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.search.domain.models.SearchViewState
import com.example.playlistmaker.search.domain.api.SearchHistoryInteractor
import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.main.domain.models.TrackApp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
    private val searchHistoryInteractor: SearchHistoryInteractor
) : ViewModel() {
    private var textTrack: String = ""
    private val _searchViewModelState = MutableLiveData<SearchViewState>()
    val searchViewModelState: LiveData<SearchViewState> get() = _searchViewModelState
    private var searchJob: Job? = null
    private var searchTrackResultList = arrayListOf<TrackApp>()

    fun writeOneTrack(track: TrackApp) {
        searchHistoryInteractor.writeOneTrack(track)
        getHistoryTrackList()
    }

    fun getHistoryTrackList() {

        viewModelScope.launch {
            searchHistoryInteractor.getTrackList().collect {
                _searchViewModelState.postValue(
                    SearchViewState.SearchViewStateDataHistory(
                        trackListHistory = it as ArrayList<TrackApp>
                    )
                )
            }
        }

    }

    fun removeTrackListInSharedPreferences() {
        searchHistoryInteractor.removeTrackListInSharedPreferences()
        getHistoryTrackList()
    }

    private fun searchTrack() {

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY_MILLIS)
            _searchViewModelState.postValue(SearchViewState.Loading)
            searchInteractor.searchTrack(textTrack)
                .collect { response ->
                    if (response != null) {
                        if (response.results.size != 0) {
                            searchTrackResultList = response.results
                            _searchViewModelState.postValue(
                                SearchViewState.SearchViewStateData(
                                    trackList = searchTrackResultList
                                )
                            )
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

    fun refreshTrackDatabaseStatus() {
        viewModelScope.launch {
            searchInteractor.refreshTrackDatabaseStatus().collect { response ->
                if (response != null) {
                    if (response.results.size != 0) {
                        searchTrackResultList = response.results
                        _searchViewModelState.postValue(
                            SearchViewState.SearchViewStateData(
                                trackList = searchTrackResultList
                            )
                        )
                    } else {
                        _searchViewModelState.postValue(SearchViewState.Empty)
                    }

                } else {
                    _searchViewModelState.postValue(SearchViewState.Error)
                }
            }
        }
    }

    override fun onCleared() {
        searchJob?.cancel()
    }

    companion object {
        private const val teg = "SearchActivity"
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
    }
}