package com.example.smartshopping2.api.request

class SignupRequest(
    val userId : String?,
    val password : String?,
    val nickName : String?,
    val name : String?
) {
    fun isNotValidID() = userId.isNullOrBlank()

    fun isNotValidPassword() =
        password.isNullOrBlank() || password.length !in 2..20

    fun isNotValidNickName() =
        nickName.isNullOrBlank() || nickName.length !in 2..20

    fun isNotValidName() = name.isNullOrBlank()
}