package com.example.smartshopping2.api

import com.example.smartshopping2.api.request.SigninRequest
import com.example.smartshopping2.api.request.SignupRequest
import com.example.smartshopping2.api.response.ApiResponse
import com.example.smartshopping2.api.response.SigninResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SmartShoppingApi {

    @GET("/api/v1/hello")
    suspend fun hello() : ApiResponse<String>

    @POST("/api/v1/signin")
    suspend fun signin(@Body signinRequest: SigninRequest) : ApiResponse<SigninResponse>

    @POST("/api/v1/users")
    suspend fun signup(@Body signupRequest: SignupRequest) : ApiResponse<Void>

    @POST("/api/v1/users/id")
    suspend fun validateUserId(@Query("userId") userId : String) : ApiResponse<Void>

    @POST("/api/v1/users/nickname")
    suspend fun validateNickName(@Query("nickName") nickName : String) : ApiResponse<Void>

    companion object{
        val instance = ApiGenerator().generate(SmartShoppingApi::class.java)
    }
}