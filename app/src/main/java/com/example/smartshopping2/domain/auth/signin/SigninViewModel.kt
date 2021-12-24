package com.example.smartshopping2.domain.auth.signin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.smartshopping2.api.SmartShoppingApi
import com.example.smartshopping2.api.request.SigninRequest
import com.example.smartshopping2.api.response.ApiResponse
import com.example.smartshopping2.api.response.SigninResponse
import com.example.smartshopping2.common.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.toast.toast
import java.lang.ref.WeakReference

class SigninViewModel(app : Application) : AndroidViewModel(app) {

    var navigatorRef : WeakReference<SigninNavigator>? = null
    private val navigator get() = navigatorRef?.get()

    val userId = MutableLiveData("")
    val password = MutableLiveData("")

    val auto_signin = MutableLiveData(false)

    fun signin() = viewModelScope.launch{
        val request = SigninRequest(userId.value, password.value)

        if (!isNotValidateSignin(request)) {
            try {
                val response = requestSignin(request)
                onSigninResponse(response)
            } catch (e: Exception) {
                Log.e("SigninViewModel", "sign-in failure.", e)
                toast(e.message ?: "알 수 없는 오류가 발생했습니다.")
            }
        }
    }

    fun auto_signin(id : String, pw : String)=viewModelScope.launch{
        val request = SigninRequest(id, pw)

        if (!isNotValidateSignin(request)){
            try {
                val response = requestSignin(request)
                onSigninResponse(response)
            } catch (e : Exception){
                Log.e("SigninViewModel","sign-in failure.", e)
                toast(e.message ?: "알 수 없는 오류가 발생했습니다.")
            }
        }
    }

   private fun isNotValidateSignin(request: SigninRequest) =
        when{
            request.isNotValidUserId() -> {
                toast("아이디를 확인해주세요.")
                true
            }
            request.isNotValidPassword() -> {
                toast("비밀번호를 다시 확인해주세요.")
                true
            }
            else -> false
        }

    private suspend fun requestSignin(request: SigninRequest) =
        withContext(Dispatchers.IO){
            SmartShoppingApi.instance.signin(request)
        }

    private fun onSigninResponse(response: ApiResponse<SigninResponse>){
        if (response.success && response.data != null){
            Prefs.token = response.data.token
            Prefs.refreshToken = response.data.refreshToken
            Prefs.nickName = response.data.nickName
            Prefs.userCode = response.data.userCode

            if (auto_signin.value == true){
                Prefs.userId = userId.value
                Prefs.password = password.value
            }

            toast("로그인 성공")
            //todo: start main activity
        }
    }
}