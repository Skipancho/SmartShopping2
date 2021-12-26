package com.example.smartshopping2.api

import com.example.smartshopping2.api.response.ApiResponse
import com.example.smartshopping2.common.Prefs
import com.example.smartshopping2.common.clearTasksAndStartActivity
import com.example.smartshopping2.domain.auth.Auth
import com.example.smartshopping2.domain.auth.signin.SigninActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import splitties.toast.toast

class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code() == 401){
            return runBlocking {
                val tokenResponse = refreshToken()

                handleTokenResponse(tokenResponse)

                Prefs.token?.let { token ->
                    response.request()
                        .newBuilder()
                        .header("Authorization",token)
                        .build()
                }
            }
        }
        return null
    }

    private suspend fun refreshToken() =
        withContext(Dispatchers.IO){
            try {
                RefreshTokenApi.refreshToken()
            }catch (e : Exception){
                ApiResponse.error<String>("인증 실패")
            }
        }

    private fun handleTokenResponse(tokenResponse: ApiResponse<String>){
        if (tokenResponse.success && tokenResponse.data != null){
            Auth.refreshToken(tokenResponse.data)
        }else{
            Auth.signout()
            clearTasksAndStartActivity<SigninActivity>()
        }
    }
}