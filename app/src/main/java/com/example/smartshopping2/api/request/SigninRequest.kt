package com.example.smartshopping2.api.request

class SigninRequest(
    val userId : String?,
    val password : String?
) {
    fun isNotValidUserId() = userId.isNullOrBlank()

    fun isNotValidPassword() =
        password.isNullOrBlank() || password.length !in 2..20
}