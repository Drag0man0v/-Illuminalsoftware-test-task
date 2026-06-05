package com.example.test_task.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {
    @GET("coins/markets")
    suspend fun getCoins(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1
    ): List<CoinDto>
}