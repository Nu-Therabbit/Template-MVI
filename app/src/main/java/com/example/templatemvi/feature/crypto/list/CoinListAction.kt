package com.example.templatemvi.feature.crypto.list

import com.example.templatemvi.feature.crypto.model.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi) : CoinListAction
}