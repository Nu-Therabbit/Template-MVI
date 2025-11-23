package com.example.templatemvi.data.crypto.model

import kotlinx.serialization.Serializable

@Serializable
data class CoinListResponse(val data: List<CoinNetworkModel>)