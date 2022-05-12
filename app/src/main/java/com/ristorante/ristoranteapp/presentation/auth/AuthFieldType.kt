package com.ristorante.ristoranteapp.presentation.auth

sealed class AuthFieldType {
    data class Email(val email: String) : AuthFieldType()
    data class Password(val password: String) : AuthFieldType()
}
