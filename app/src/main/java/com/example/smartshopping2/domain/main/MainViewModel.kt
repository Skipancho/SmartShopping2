package com.example.smartshopping2.domain.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.smartshopping2.api.RefreshTokenApi
import com.example.smartshopping2.api.SmartShoppingApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import splitties.toast.toast
import java.lang.ref.WeakReference

class MainViewModel(app:Application) : AndroidViewModel(app){

    var navigatorRef : WeakReference<MainNavigator>? = null
    private val navigator get() = navigatorRef?.get()

    val textdata = MutableLiveData("")

    fun hello() = viewModelScope.launch {
        val response = SmartShoppingApi.instance.hello()

        textdata.value = response.toString()
    }

    fun startMyPage(){
        navigator?.startMyPage()
    }
}