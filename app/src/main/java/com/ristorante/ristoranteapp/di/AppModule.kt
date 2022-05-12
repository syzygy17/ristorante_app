package com.ristorante.ristoranteapp.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ristorante.ristoranteapp.data.repository.DefaultAuthRepository
import com.ristorante.ristoranteapp.data.repository.DefaultHomeRepository
import com.ristorante.ristoranteapp.data.repository.DefaultRegistrationRepository
import com.ristorante.ristoranteapp.domain.auth.AuthRepository
import com.ristorante.ristoranteapp.domain.home.HomeRepository
import com.ristorante.ristoranteapp.domain.registration.RegistrationRepository
import com.ristorante.ristoranteapp.presentation.auth.AuthViewModel
import com.ristorante.ristoranteapp.presentation.home.HomeViewModel
import com.ristorante.ristoranteapp.presentation.registration.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    factory {
        Firebase.auth
    }
    factory {
        Firebase.database.reference
    }
    factory {
        DefaultRegistrationRepository(
            firebaseAuth = get(),
            database = get()
        )
    }.bind(RegistrationRepository::class)
    factory {
        DefaultAuthRepository(
            firebaseAuth = get()
        )
    }.bind(AuthRepository::class)
    factory {
        DefaultHomeRepository()
    }.bind(HomeRepository::class)
    viewModel {
        RegistrationViewModel(
            registrationRepository = get()
        )
    }
    viewModel {
        AuthViewModel(
            authRepository = get()
        )
    }
    viewModel {
        HomeViewModel(
            homeRepository = get()
        )
    }
}