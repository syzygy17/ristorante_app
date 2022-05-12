package com.ristorante.ristoranteapp.presentation.registration

sealed class RegistrationViewState {
    data class Registered(val login: String) : RegistrationViewState()
}
