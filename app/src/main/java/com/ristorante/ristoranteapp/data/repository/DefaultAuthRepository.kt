package com.ristorante.ristoranteapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.ristorante.ristoranteapp.domain.Response
import com.ristorante.ristoranteapp.domain.auth.AuthRepository
import com.ristorante.ristoranteapp.domain.registration.User
import kotlinx.coroutines.tasks.await

class DefaultAuthRepository(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signIn(user: User): Response<Boolean> = try {
        val authResult = firebaseAuth.signInWithEmailAndPassword(user.email, user.password).await()
        Response.Success(authResult.user != null)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override fun isUserAuthorized(): Boolean = firebaseAuth.currentUser != null
}