package com.example.templatemvi.feature.crypto.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.templatemvi.core.domain.NetworkError
import com.example.templatemvi.core.domain.onError
import com.example.templatemvi.core.domain.onSuccess
import com.example.templatemvi.domain.crypto.CoinDataSource
import com.example.templatemvi.feature.crypto.model.CoinUi
import com.example.templatemvi.feature.crypto.model.DataPoint
import com.example.templatemvi.feature.crypto.model.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CoinListViewModel(private val coinDataSource: CoinDataSource) : ViewModel() {

    private val _state = MutableStateFlow<CoinListState>(CoinListState.Idle)
    val state = _state
        .onStart { loadCoins() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = CoinListState.Idle,
        )

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {
                selectCoin(action.coinUi)
            }
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.value = CoinListState.Loading

            coinDataSource
                .getCoins()
                .onSuccess { coins ->
                    _state.value = CoinListState.Success(
                        coins = coins.map { it.toCoinUi() },
                    )
                }
                .onError { error ->
                    _state.value = CoinListState.Error(error)
                }
        }
    }

    private fun selectCoin(coinUi: CoinUi) {
        _state.update {
            if (it is CoinListState.Success) {
                it.copy(selectedCoin = coinUi)
            } else {
                it
            }
        }
        viewModelScope.launch {
            coinDataSource
                .getCoinHistory(
                    coinId = coinUi.id,
                    start = ZonedDateTime.now().minusDays(5),
                    end = ZonedDateTime.now()
                )
                .onSuccess { history ->
                    val dataPoints = history
                        .sortedBy { it.dateTime }
                        .map {
                            DataPoint(
                                x = it.dateTime.hour.toFloat(),
                                y = it.priceUsd.toFloat(),
                                xLabel = DateTimeFormatter
                                    .ofPattern("ha\nM/d")
                                    .format(it.dateTime)
                            )
                        }

                    _state.update {
                        if (it is CoinListState.Success) {
                            it.copy(selectedCoin = it.selectedCoin?.copy(coinPriceHistory = dataPoints))
                        } else {
                            it
                        }
                    }
                }
                .onError { error ->
                    _state.value = CoinListState.Error(error)
                }
        }
    }
}