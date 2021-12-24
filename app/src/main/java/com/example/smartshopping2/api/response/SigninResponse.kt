package com.example.smartshopping2.api.response

data class SigninResponse(
    val token : String,
    val refreshToken : String,
    val nickName : String,
    val userCode : Long
)
