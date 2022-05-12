package com.ristorante.ristoranteapp.presentation.registration

sealed class RegistrationFieldType {
    data class Login(val login: String) : RegistrationFieldType()
    data class Email(val email: String) : RegistrationFieldType()
    data class Password(val password: String) : RegistrationFieldType()
}
