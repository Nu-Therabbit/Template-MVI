package com.example.templatemvi.data.crypto

import com.example.templatemvi.core.data.network.constructUrl
import com.example.templatemvi.core.data.network.safeCall
import com.example.templatemvi.core.domain.DomainError
import com.example.templatemvi.core.domain.Result
import com.example.templatemvi.core.domain.map
import com.example.templatemvi.data.crypto.mapper.toCoin
import com.example.templatemvi.data.crypto.model.CoinListResponse
import com.example.templatemvi.domain.crypto.Coin
import com.example.templatemvi.domain.crypto.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import com.example.templatemvi.BuildConfig
import com.example.templatemvi.core.domain.NetworkError
import com.example.templatemvi.data.crypto.mapper.toCoinPrice
import com.example.templatemvi.data.crypto.model.CoinHistoryResponse
import com.example.templatemvi.domain.crypto.CoinPrice
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

class CoinRemoteDataSource(private val httpClient: HttpClient) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, DomainError> {
        return safeCall<CoinListResponse> {
            httpClient.get(
                urlString = constructUrl(
                    baseUel = BuildConfig.BASE_URL,
                    url = "/assets",
                )
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        val endMillis = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        return safeCall<CoinHistoryResponse> {
            httpClient.get(
                urlString = constructUrl(
                    url = "/assets/$coinId/history",
                    baseUel = BuildConfig.BASE_URL,
                )
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}