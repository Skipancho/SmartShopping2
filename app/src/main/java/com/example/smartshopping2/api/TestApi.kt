package com.example.smartshopping2.api

import com.example.smartshopping2.api.response.TestApiResponse
import com.example.smartshopping2.api.response.testResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TestApi {

    @GET("/SC/S_C_ProductList.php")
    suspend fun getProducts(
        @Query("mode") mode : String,
        @Query("searchText") searchText : String?
    ): TestApiResponse<List<testResponse>>

    companion object{
        val instance = ApiGenerator().testG(TestApi::class.java)
    }
}