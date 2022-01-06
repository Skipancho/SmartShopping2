package com.example.smartshopping2.api.request

data class ReviewRequest(
    val productId : Long,
    val score : Int,
    val reviewText : String
)
