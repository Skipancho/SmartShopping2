package com.example.smartshopping2.api

import com.example.smartshopping2.App
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiGenerator {
    fun <T> generate(api : Class<T>) : T = Retrofit.Builder()
        .baseUrl("${App.API_HOST}:${App.API_PORT}")
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient())
        .build()
        .create(api)

    fun <T> testG(api : Class<T>) : T = Retrofit.Builder()
        .baseUrl("${App.TEST_HOST}")
        .addConverterFactory(GsonConverterFactory.create())
        .client(testClient())
        .build()
        .create(api)

    fun <T> generateRefreshClient(api : Class<T>) : T = Retrofit.Builder()
        .baseUrl("${App.API_HOST}:${App.API_PORT}")
        .addConverterFactory(GsonConverterFactory.create())
        .client(refreshClient())
        .build()
        .create(api)

    private fun testClient() =
        OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor())
        }.build()

    private fun httpClient() =
        OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor())
            addInterceptor(ApiTokenInterceptor())
            authenticator(TokenAuthenticator())
        }.build()

    private fun refreshClient() =
        OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor())
            addInterceptor(RefreshTokenInterceptor())
        }.build()

    private fun httpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
}