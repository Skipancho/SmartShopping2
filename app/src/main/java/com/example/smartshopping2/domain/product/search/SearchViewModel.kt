package com.example.smartshopping2.domain.product.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.smartshopping2.api.SmartShoppingApi
import com.example.smartshopping2.domain.product.ProductStatus
import com.example.smartshopping2.domain.product.list.ProductListAdapter
import com.example.smartshopping2.domain.product.list.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.toast.toast
import java.lang.ref.WeakReference
import java.text.NumberFormat

class SearchViewModel(app : Application) : AndroidViewModel(app) , ProductListAdapter.OnItemClickListenr {

    var navigatorRef : WeakReference<SearchNavigator>? = null
    private val navigator get() = navigatorRef?.get()

    val search_text = MutableLiveData("")
    val search_products = mutableListOf<ProductModel>()

    fun searchProduct() = viewModelScope.launch {
        search_products.clear()

        val response =
        SmartShoppingApi.instance.getProducts(Long.MAX_VALUE,null,"next",search_text.value)
        val datas = response.data

        if (!datas.isNullOrEmpty()) {
            for (data in datas){
                val soldOutString =
                    if (ProductStatus.SOLD_OUT == data.status)
                        "(품절)"
                    else ""

                val commaSeparatedPrice =
                    NumberFormat.getNumberInstance().format(data.price)
                search_products.add(ProductModel(data.id,
                    data.name,
                    data.description,
                    "₩$commaSeparatedPrice $soldOutString",
                    data.status,
                    data.sellerId,
                    data.imagePaths))
            }
        }

        withContext(Dispatchers.Main){
            navigator?.listUpdate()
        }
    }

    fun finish(){
        navigator?.finishActivity()
        //clearTasksAndStartActivity<MainActivity>()
    }

    override fun onItemClick(productId: Long?) {
        toast("test : $productId")
    }
}