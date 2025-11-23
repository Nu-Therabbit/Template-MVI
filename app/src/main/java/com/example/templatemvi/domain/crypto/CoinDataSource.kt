package com.example.templatemvi.domain.crypto

import com.example.templatemvi.core.domain.DomainError
import com.example.templatemvi.core.domain.NetworkError
import com.example.templatemvi.core.domain.Result.Error
import com.example.templatemvi.core.domain.Result
import java.time.ZonedDateTime

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, DomainError>
    suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError>
}