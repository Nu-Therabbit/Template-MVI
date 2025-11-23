package com.example.templatemvi

import android.app.Application
import com.example.templatemvi.core.di.coreModule
import com.example.templatemvi.data.crypto.di.dataModule
import com.example.templatemvi.feature.crypto.di.cryptoModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TemplateApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TemplateApplication)
            androidLogger()

            modules(coreModule, dataModule, cryptoModule)
        }
    }
}