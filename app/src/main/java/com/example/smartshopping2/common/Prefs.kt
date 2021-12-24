package com.example.smartshopping2.common

import androidx.preference.PreferenceManager
import com.example.smartshopping2.App

object Prefs {
    private const val TOKEN = "token"
    private const val REFRESH_TOKEN = "refresh_token"
    private const val USER_NICKNAME = "user_nickname"
    private const val USER_CODE = "user_code"

    private const val USER_ID = "user_id"
    private const val USER_PW = "user_pw"

    val prefs by lazy {
        PreferenceManager.getDefaultSharedPreferences(App.instance)
    }

    var token
        get() = prefs.getString(TOKEN, null)
        set(value) = prefs.edit().putString(TOKEN, value).apply()

    var refreshToken
        get() = prefs.getString(REFRESH_TOKEN, null)
        set(value) = prefs.edit().putString(REFRESH_TOKEN, value).apply()

    var nickName
        get() = prefs.getString(USER_NICKNAME, null)
        set(value) = prefs.edit().putString(USER_NICKNAME, value).apply()

    var userCode
        get() = prefs.getLong(USER_CODE, 0)
        set(value) = prefs.edit().putLong(USER_CODE,value).apply()

    var userId
        get() = prefs.getString(USER_ID, null)
        set(value) = prefs.edit().putString(USER_ID,value).apply()

    var password
        get() = prefs.getString(USER_PW, null)
        set(value) = prefs.edit().putString(USER_PW,value).apply()

}