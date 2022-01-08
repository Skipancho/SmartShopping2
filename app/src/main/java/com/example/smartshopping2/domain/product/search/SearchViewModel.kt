package com.example.smartshopping2.domain.product.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.smartshopping2.api.SmartShoppingApi
import com.example.smartshopping2.common.Prefs
import com.example.smartshopping2.domain.product.ProductStatus
import com.example.smartshopping2.domain.product.list.ProductListAdapter
import com.example.smartshopping2.domain.product.list.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.toast.toast
import java.lang.ref.WeakReference
import java.text.NumberFormat
import java.util.ArrayList

class SearchViewModel(app : Application) : AndroidViewModel(app) , ProductListAdapter.OnItemClickListenr {

    var navigatorRef : WeakReference<SearchNavigator>? = null
    private val navigator get() = navigatorRef?.get()

    val search_text = MutableLiveData("")
    val search_products = mutableListOf<ProductModel>()

    val isProudctsEmpty = MutableLiveData(false)
    val isBeforeSearch = MutableLiveData(true)

    val search_tags = Prefs.searchList

    fun searchProduct() = viewModelScope.launch {
        val searchText = search_text.value
        searchText?.let {
            val s_list = Prefs.searchList
            s_list.add(it)

            Prefs.searchList = s_list
        }

        search_products.clear()

        val response =
        SmartShoppingApi.instance.getProducts(Long.MAX_VALUE,null,"next",searchText)
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
            if (datas.isNullOrEmpty()){
                toast("해당하는 상품이 없습니다.")
                isProudctsEmpty.value = true
            } else {
                isProudctsEmpty.value = false
            }
            isBeforeSearch.value = false
            navigator?.listUpdate()
        }
    }

    fun search_tag_clear(){
        search_tags.clear()
        Prefs.searchList = search_tags
        navigator?.listUpdate()
    }

    fun finish(){
        navigator?.finishActivity()
        //clearTasksAndStartActivity<MainActivity>()
    }

    override fun onItemClick(productId: Long?) {
        navigator?.startProductDetailActivity(productId)
    }
}