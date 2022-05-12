package com.ristorante.ristoranteapp.presentation.auth

sealed class AuthViewState {
    object Authorized : AuthViewState()
}
