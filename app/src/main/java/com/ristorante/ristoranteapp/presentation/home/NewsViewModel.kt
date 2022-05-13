package com.ristorante.ristoranteapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ristorante.ristoranteapp.domain.Response
import com.ristorante.ristoranteapp.domain.home.News
import com.ristorante.ristoranteapp.domain.home.NewsRepository
import com.ristorante.ristoranteapp.domain.presentation.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState<NewsViewState>>()

    val viewState: LiveData<ViewState<NewsViewState>> = _viewState

    init {
        onGetNews()
    }

    private fun onGetNews() {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            getNews()
        }
    }

    private suspend fun getNews() = withContext(Dispatchers.IO) {
        when (val result = newsRepository.getNews()) {
            is Response.Success -> onNewsFetched(result.data)
            is Response.Failure -> onError(result.data)
        }
    }

    private fun onNewsFetched(news: List<News>) =
        _viewState.postValue(ViewState.Data(NewsViewState.NewsFetched(news)))

    private fun onError(throwable: Throwable) =
        _viewState.postValue(ViewState.Error(throwable))
}