package com.ristorante.ristoranteapp.domain.adminpanel

import com.ristorante.ristoranteapp.domain.Response

interface AdminPanelRepository {

    suspend fun saveNews(news: MutableNews): Response<Boolean>
}