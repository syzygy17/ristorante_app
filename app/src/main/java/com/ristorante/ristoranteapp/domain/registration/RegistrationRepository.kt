package com.ristorante.ristoranteapp.domain.registration

import com.ristorante.ristoranteapp.domain.Response

interface RegistrationRepository {

    suspend fun signUp(user: User): Response<Boolean>
}