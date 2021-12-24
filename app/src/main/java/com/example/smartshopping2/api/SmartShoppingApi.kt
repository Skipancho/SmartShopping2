package com.example.smartshopping2.api

import com.example.smartshopping2.api.request.SigninRequest
import com.example.smartshopping2.api.response.ApiResponse
import com.example.smartshopping2.api.response.SigninResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SmartShoppingApi {

    @POST("/api/v1/signin")
    suspend fun signin(@Body signinRequest: SigninRequest) : ApiResponse<SigninResponse>

    companion object{
        val instance = ApiGenerator().generate(SmartShoppingApi::class.java)
    }
}