package com.ristorante.ristoranteapp.presentation.menu

import com.ristorante.ristoranteapp.domain.menu.Menu

sealed class MenuViewState {
    data class MenuFetched(val menu: List<Menu>) : MenuViewState()
}