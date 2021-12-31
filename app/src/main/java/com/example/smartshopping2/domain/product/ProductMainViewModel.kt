package com.example.smartshopping2.domain.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.smartshopping2.domain.main.MainNavigator
import splitties.toast.toast
import java.lang.ref.WeakReference

class ProductMainViewModel(app:Application)
    : AndroidViewModel(app)
{
    var navigatorRef : WeakReference<ProductMainNavigator>? = null
    private val navigator get() = navigatorRef?.get()

    fun startSearchProductActivity(){
        navigator?.startSearchActivity()
    }
}