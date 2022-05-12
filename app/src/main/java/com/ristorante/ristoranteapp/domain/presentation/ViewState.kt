package com.ristorante.ristoranteapp.domain.presentation

sealed class ViewState<out T : Any> {
    data class Data<out T : Any>(val data: T) : ViewState<T>()
    object Loading : ViewState<Nothing>()
    data class Error(val throwable: Throwable) : ViewState<Nothing>()
}
