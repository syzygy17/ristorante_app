package com.ristorante.ristoranteapp.presentation

import android.app.Application
import com.ristorante.ristoranteapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RistoranteApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@RistoranteApp)
            modules(appModule)
        }
    }
}