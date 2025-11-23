package com.example.templatemvi.data.crypto.model

import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryResponse(
    val data: List<CoinPriceNetworkModel>
)