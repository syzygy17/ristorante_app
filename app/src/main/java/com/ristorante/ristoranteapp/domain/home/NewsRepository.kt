package com.ristorante.ristoranteapp.domain.home

import com.ristorante.ristoranteapp.domain.Response

interface NewsRepository {

    suspend fun getNews(): Response<List<News>>
}