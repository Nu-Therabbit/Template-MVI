package com.example.templatemvi.data.crypto.model

import kotlinx.serialization.Serializable

@Serializable
data class CoinPriceNetworkModel(
    val priceUsd: Double,
    val time: Long
)