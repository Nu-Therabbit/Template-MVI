package com.example.templatemvi.core.di

import com.example.templatemvi.core.data.network.HttpClientFactory
import io.ktor.client.engine.cio.CIO
import org.koin.dsl.module

val coreModule = module {
    single { HttpClientFactory.create(CIO.create()) }
}