package com.example.test_task.data.repository

import com.example.test_task.data.api.RetrofitInstance
import com.example.test_task.data.api.CoinDto

class CryptoRepository {

    suspend fun getCoins(): Result<List<CoinDto>> {
        return try {
            val coins = RetrofitInstance.api.getCoins()
            Result.success(coins)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}