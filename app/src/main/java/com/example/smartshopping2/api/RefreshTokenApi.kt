package com.example.smartshopping2.api

import com.example.smartshopping2.api.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RefreshTokenApi {

    @POST("/api/v1/refresh_token")
    suspend fun refreshToken(
        @Query("grant_type") grantType : String = "refresh_token"
    ) : ApiResponse<String>

    companion object{
        private val instance = ApiGenerator()
            .generateRefreshClient(RefreshTokenApi::class.java)

        suspend fun refreshToken() = instance.refreshToken()
    }
}