package com.example.test_task.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//singleton
object RetrofitInstance {
    private const val BASE_URL = "https://api.coingecko.com/api/v3/"

    private val client = OkHttpClient.Builder()
        // Logs all requests and responses in Logcat
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        // Automatically adds API key to every request
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-cg-demo-api-key", "CG-8PBL7s945SsZHvgv8imjXr6j") //TODO: move to local.properties before release
                .build()
            chain.proceed(request)
        }
        .build()

    val api: CryptoApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoApi::class.java)
    }
}