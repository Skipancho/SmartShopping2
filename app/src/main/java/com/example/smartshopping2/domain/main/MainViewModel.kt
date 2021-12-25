package com.example.smartshopping2.domain.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import java.lang.ref.WeakReference

class MainViewModel(app:Application) : AndroidViewModel(app){

    var navigatorRef : WeakReference<MainNavigator>? = null
    private val navigator get() = navigatorRef?.get()

    fun startMyPage(){
        navigator?.startMyPage()
    }
}