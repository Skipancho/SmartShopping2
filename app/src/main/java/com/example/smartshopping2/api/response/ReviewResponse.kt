package com.example.smartshopping2.api.response

data class ReviewResponse(
    val nickName : String,
    val productName : String,
    val score : Int,
    val reviewText : String,
    val date : String
)
