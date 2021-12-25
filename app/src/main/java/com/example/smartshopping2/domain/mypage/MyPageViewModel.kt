package com.example.smartshopping2.domain.mypage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.smartshopping2.common.Prefs
import com.example.smartshopping2.common.clearTasksAndStartActivity
import com.example.smartshopping2.domain.auth.Auth
import com.example.smartshopping2.domain.auth.signin.SigninActivity
import splitties.toast.toast

class MyPageViewModel(app:Application) : AndroidViewModel(app) {

    val nickName = MutableLiveData(Prefs.nickName)
    val userCode = MutableLiveData("회원번호#"+Prefs.userCode)

    fun signout(){
        Auth.signout()
        clearTasksAndStartActivity<SigninActivity>()
        toast("sign out")
    }
}