package com.example.playlistmaker.search_activity.ui


import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.search_activity.domain.models.ResponseModel
import com.example.playlistmaker.search_activity.domain.models.SearchViewState
import com.example.playlistmaker.search_activity.domain.api.SearchHistoryInteractor
import com.example.playlistmaker.search_activity.domain.api.SearchInteractor
import com.example.playlistmaker.TrackDtoApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
    private val searchHistoryInteractor: SearchHistoryInteractor
) : ViewModel() {
    private var textTrack: String = ""
    private val _searchViewModelState = MutableLiveData<SearchViewState>()
    val searchViewModelState: LiveData<SearchViewState> get() = _searchViewModelState
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { searchTrack() }

    init {
        Log.d(teg, "init")
        getHistoryTrackList()
    }

    fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY_MILLIS)
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
        val response = searchInteractor.searchTrack(textTrack)
        response?.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    if (response.body()?.results?.size != 0) {
                        _searchViewModelState.postValue(response.body()?.results?.let {
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

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                _searchViewModelState.postValue(SearchViewState.Error)
            }
        })

    }

    fun setTextTrack(text: String) {
        textTrack = text
        searchDebounce()
    }

    override fun onCleared() {
        handler.removeCallbacksAndMessages(searchRunnable)
    }

    companion object {
        private const val teg = "SearchActivity"
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
    }
}