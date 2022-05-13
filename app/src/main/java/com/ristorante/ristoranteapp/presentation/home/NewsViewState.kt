package com.ristorante.ristoranteapp.presentation.home

import com.ristorante.ristoranteapp.domain.home.News

sealed class NewsViewState {
    data class NewsFetched(val news: List<News>) : NewsViewState()
}
