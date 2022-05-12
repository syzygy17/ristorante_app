package com.ristorante.ristoranteapp.domain.auth

import com.ristorante.ristoranteapp.domain.Response
import com.ristorante.ristoranteapp.domain.registration.User

interface AuthRepository {

    suspend fun signIn(user: User): Response<Boolean>
    fun isUserAuthorized(): Boolean
}