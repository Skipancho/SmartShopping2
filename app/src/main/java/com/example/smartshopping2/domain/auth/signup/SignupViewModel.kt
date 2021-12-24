package com.example.smartshopping2.domain.auth.signup

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.smartshopping2.api.SmartShoppingApi
import com.example.smartshopping2.api.request.SignupRequest
import com.example.smartshopping2.api.response.ApiResponse
import com.example.smartshopping2.common.clearTasksAndStartActivity
import com.example.smartshopping2.domain.auth.signin.SigninActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.toast.toast
import java.lang.Exception

class SignupViewModel(app : Application) : AndroidViewModel(app) {

    val userId = MutableLiveData("")
    val password = MutableLiveData("")
    val passwordChecker = MutableLiveData("")

    val nickName = MutableLiveData("")
    val userName = MutableLiveData("")

    val isUserIdValid = MutableLiveData(false)
    val isNickNameValid = MutableLiveData(false)

    val isPasswordSame = MutableLiveData(false)

    val passwordCheckText = MutableLiveData("")

    fun signup() = viewModelScope.launch {
        var request = SignupRequest(userId.value,password.value,nickName.value,userName.value)
        if (!isNotValidSignup(request)){
            if (isUserIdValid.value == false){
                toast("아이디 중복확인을 해주세요.")
                return@launch
            }
            if (isNickNameValid.value == false){
                toast("닉네임 중복확인을 해주세요.")
                return@launch
            }
            try {
                val response = requestSignup(request)
                onSignupResponse(response)
            }catch (e:Exception){
                Log.e("SignupViewModel","sign-up failure",e)
                toast("회원가입 실패")
            }
        }
    }


    fun idCheck() = viewModelScope.launch {
        val id = userId.value

        if (!id.isNullOrBlank()){
            try {
                val response = validateId(id)
                validateHandler(response)
                if (response.success){
                    isUserIdValid.value = true
                }
            }catch (e : Exception){
                Log.e("SignupViewModel","idCheck failure",e)
                toast(e.message ?: "unknown error")
            }
        }
    }

    fun nickNameCheck() = viewModelScope.launch {
        val nickName = nickName.value

        if (!nickName.isNullOrBlank()){
            try {
                val response = validateNickName(nickName)
                validateHandler(response)
                if (response.success){
                    isNickNameValid.value = true
                }
            }catch (e : Exception){
                Log.e("SignupViewModel","nickNameCheck failure",e)
                toast(e.message ?: "unknown error")
            }
        }
    }

    private fun isNotValidSignup(signupRequest: SignupRequest) =
        when{
            signupRequest.isNotValidID() -> {
                toast("아이디를 확인해주세요.")
                true
            }
            signupRequest.isNotValidPassword()->{
                toast("비밀번호를 확인해주세요.")
                true
            }
            signupRequest.isNotValidNickName()->{
                toast("닉네임을 확인해주세요.")
                true
            }
            signupRequest.isNotValidName()->{
                toast("이름을 확이해주세요.")
                true
            }
            else -> false
        }

    private suspend fun validateId(userId : String) =
        withContext(Dispatchers.IO){
            SmartShoppingApi.instance.validateUserId(userId)
        }

    private suspend fun validateNickName(nickName : String) =
        withContext(Dispatchers.IO){
            SmartShoppingApi.instance.validateNickName(nickName)
        }

    private suspend fun requestSignup(request: SignupRequest) =
        withContext(Dispatchers.IO){
            SmartShoppingApi.instance.signup(request)
        }

    private fun onSignupResponse(response: ApiResponse<Void>){
        if (response.success){
            toast("회원 가입이 완료되었습니다. 로그인 후 이용해주세요.")
            clearTasksAndStartActivity<SigninActivity>()
        }else{
            toast(response.message ?: "unknown error")
        }
    }

    private fun validateHandler(response: ApiResponse<Void>){
        if (response.success){
            toast("사용 가능합니다.")
        }else{
            toast(response.message ?: "unknown error")
        }
    }

    fun onEditTextWatcher() : TextWatcher{
        return object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (password.value == passwordChecker.value){
                    passwordCheckText.value = "비밀번호가 일치합니다."
                    isPasswordSame.value = true
                }else{
                    passwordCheckText.value = "비밀번호가 일치하지 않습니다."
                    isPasswordSame.value = false
                }
            }
        }
    }

}