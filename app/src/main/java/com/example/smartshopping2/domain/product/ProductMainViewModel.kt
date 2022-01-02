package com.example.smartshopping2.domain.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.smartshopping2.domain.main.MainNavigator
import com.example.smartshopping2.domain.product.list.ProductListAdapter
import splitties.toast.toast
import java.lang.ref.WeakReference

class ProductMainViewModel(app:Application)
    : AndroidViewModel(app),
        ProductListAdapter.OnItemClickListenr
{
    var navigatorRef : WeakReference<ProductMainNavigator>? = null
    private val navigator get() = navigatorRef?.get()

    fun startSearchProductActivity(){
        navigator?.startSearchActivity()
    }

    override fun onItemClick(productId: Long?) {
        navigator?.startProductDetail(productId)
    }
}