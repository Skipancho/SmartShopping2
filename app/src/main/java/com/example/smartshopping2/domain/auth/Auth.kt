package com.example.smartshopping2.domain.auth

import com.example.smartshopping2.common.Prefs

object Auth {
    fun signin(
        token : String,
        refreshToken : String,
        nickName : String,
        userCode : Long
    ){
        Prefs.token = token
        Prefs.refreshToken = refreshToken
        Prefs.nickName = nickName
        Prefs.userCode = userCode

        println("token : $token")
        println("refresh : $refreshToken")
    }

    fun signout(){
        Prefs.token = null
        Prefs.refreshToken = null
        Prefs.nickName = null
        Prefs.userCode = 0
        Prefs.userId = null
    }

    fun refreshToken(token: String){
        Prefs.token = token
    }
}