package com.example.templatemvi.feature.crypto.list

import androidx.compose.runtime.Immutable
import com.example.templatemvi.core.domain.DomainError
import com.example.templatemvi.feature.crypto.model.CoinUi

sealed class CoinListState {
    data object Idle : CoinListState()
    data object Loading : CoinListState()

    @Immutable
    data class Success(
        val coins: List<CoinUi> = emptyList(),
        val selectedCoin: CoinUi? = null
    ) : CoinListState()

    data class Error(val error: DomainError) : CoinListState()
}