package com.ristorante.ristoranteapp.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ristorante.ristoranteapp.data.repository.DefaultAdminPanelRepository
import com.ristorante.ristoranteapp.data.repository.DefaultAuthRepository
import com.ristorante.ristoranteapp.data.repository.DefaultNewsRepository
import com.ristorante.ristoranteapp.data.repository.DefaultRegistrationRepository
import com.ristorante.ristoranteapp.domain.adminpanel.AdminPanelRepository
import com.ristorante.ristoranteapp.domain.auth.AuthRepository
import com.ristorante.ristoranteapp.domain.home.NewsRepository
import com.ristorante.ristoranteapp.domain.registration.RegistrationRepository
import com.ristorante.ristoranteapp.presentation.adminpanel.AdminPanelViewModel
import com.ristorante.ristoranteapp.presentation.auth.AuthViewModel
import com.ristorante.ristoranteapp.presentation.home.NewsViewModel
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
        DefaultNewsRepository(
            database = get()
        )
    }.bind(NewsRepository::class)
    factory {
        DefaultAdminPanelRepository(
            database = get()
        )
    }.bind(AdminPanelRepository::class)
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
        NewsViewModel(
            newsRepository = get()
        )
    }
    viewModel {
        AdminPanelViewModel(
            adminPanelRepository = get()
        )
    }
}