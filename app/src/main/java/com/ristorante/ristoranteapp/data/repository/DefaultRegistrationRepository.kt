package com.ristorante.ristoranteapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.ristorante.ristoranteapp.domain.Response
import com.ristorante.ristoranteapp.domain.registration.RegistrationRepository
import com.ristorante.ristoranteapp.domain.registration.User
import kotlinx.coroutines.tasks.await

class DefaultRegistrationRepository(
    private val firebaseAuth: FirebaseAuth,
    private val database: DatabaseReference
) : RegistrationRepository {

    override suspend fun signUp(user: User): Response<Boolean> = try {
        val authResult =
            firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
        val isSuccessful = authResult.user != null
        if (isSuccessful) {
            database.child("users")
                .child(authResult.user?.uid ?: "")
                .child("login")
                .setValue(user.login)
                .await()
        }
        Response.Success(isSuccessful)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}