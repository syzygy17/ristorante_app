package com.ristorante.ristoranteapp.domain.menu

import com.ristorante.ristoranteapp.domain.Response

interface MenuRepository {

    suspend fun getMenu(): Response<List<Menu>>
}