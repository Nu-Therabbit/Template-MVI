package com.example.templatemvi.feature.crypto.di

import com.example.templatemvi.feature.crypto.list.CoinListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val cryptoModule = module {
    viewModel { CoinListViewModel(get()) }
}