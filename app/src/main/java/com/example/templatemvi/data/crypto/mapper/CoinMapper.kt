package com.example.templatemvi.data.crypto.mapper

import com.example.templatemvi.data.crypto.model.CoinNetworkModel
import com.example.templatemvi.data.crypto.model.CoinPriceNetworkModel
import com.example.templatemvi.domain.crypto.Coin
import com.example.templatemvi.domain.crypto.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinNetworkModel.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}

fun CoinPriceNetworkModel.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant
            .ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
    )
}