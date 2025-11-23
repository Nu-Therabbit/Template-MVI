package com.example.templatemvi.data.crypto.model

import kotlinx.serialization.Serializable

@Serializable
data class CoinNetworkModel(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double
)