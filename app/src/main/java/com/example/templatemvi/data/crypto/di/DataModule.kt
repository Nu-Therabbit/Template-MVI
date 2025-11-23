package com.example.templatemvi.data.crypto.di

import com.example.templatemvi.core.data.network.HttpClientFactory
import com.example.templatemvi.data.crypto.CoinRemoteDataSource
import com.example.templatemvi.domain.crypto.CoinDataSource
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::CoinRemoteDataSource).bind<CoinDataSource>()
}