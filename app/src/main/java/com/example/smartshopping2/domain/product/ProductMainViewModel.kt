package com.example.smartshopping2.domain.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import splitties.toast.toast

class ProductMainViewModel(app:Application)
    : AndroidViewModel(app)
{
    fun startSearchProductActivity(){
        //todo. startSearchProductActivity
        toast("hello word")
    }
}