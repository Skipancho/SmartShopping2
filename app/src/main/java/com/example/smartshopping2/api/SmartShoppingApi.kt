package com.example.smartshopping2.api

import com.example.smartshopping2.api.request.ReviewRequest
import com.example.smartshopping2.api.request.SigninRequest
import com.example.smartshopping2.api.request.SignupRequest
import com.example.smartshopping2.api.response.ApiResponse
import com.example.smartshopping2.api.response.ProductResponse
import com.example.smartshopping2.api.response.ReviewResponse
import com.example.smartshopping2.api.response.SigninResponse
import retrofit2.http.*

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

    @GET("/api/v1/products")
    suspend fun getProducts(
        @Query("productId") productId : Long,
        @Query("categoryId") categoryId : Int?,
        @Query("direction") direction : String,
        @Query("keyword") keyword : String? = null
    ):ApiResponse<List<ProductResponse>>

    @GET("/api/v1/products/{id}")
    suspend fun getProduct(
        @Path("id") id : Long
    ):ApiResponse<ProductResponse>

    @GET("/api/v1/reviews")
    suspend fun getReviews(
        @Query("userCode") userCode : Long?,
        @Query("productId") productId: Long?
    ):ApiResponse<List<ReviewResponse>>

    @POST("/api/v1/review")
    suspend fun reviewRegister(
        @Body reviewRequest: ReviewRequest
    ):ApiResponse<Void>

    companion object{
        val instance = ApiGenerator().generate(SmartShoppingApi::class.java)
    }
}