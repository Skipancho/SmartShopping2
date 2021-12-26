package com.example.smartshopping2.api

import com.example.smartshopping2.common.Prefs
import com.example.smartshopping2.common.clearTasksAndStartActivity
import com.example.smartshopping2.domain.auth.Auth
import com.example.smartshopping2.domain.auth.signin.SigninActivity
import okhttp3.Interceptor
import okhttp3.Response
import splitties.toast.toast

class RefreshTokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder().apply {
            Prefs.refreshToken?.let{ header("Authorization", it)}
            method(original.method(),original.body())
        }.build()

        val response = chain.proceed(request)

        if (response.code() == 401){
            Auth.signout()
            clearTasksAndStartActivity<SigninActivity>()
        }

        return response
    }
}