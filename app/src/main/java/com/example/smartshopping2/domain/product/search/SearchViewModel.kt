package com.example.smartshopping2.domain.product.search

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.smartshopping2.api.SmartShoppingApi
import com.example.smartshopping2.api.response.ApiResponse
import com.example.smartshopping2.api.response.ProductResponse
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
    val isTextBlank = MutableLiveData(true)

    val search_tags = Prefs.searchList
    var save_mode = Prefs.saveMode

    val mode_text = MutableLiveData(when(save_mode){
        true -> "자동저장 끄기"
        false -> "자동저장 켜기"
    })

    val isSearchTagsEmpty = MutableLiveData(search_tags.isNullOrEmpty())

    fun searchProduct() = viewModelScope.launch {
        val searchText = search_text.value
        searchText?.let {
            if (save_mode){
                search_tags.add(0,it)

                Prefs.searchList = search_tags
                isSearchTagsEmpty.value = false
            }
        }

        search_products.clear()

        val response = getProducts(searchText)

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

    private suspend fun getProducts(searchText : String?) = try {
        SmartShoppingApi.instance.getProducts(Long.MAX_VALUE,null,"next",searchText)
    }catch (e : Exception){
        Log.e("ProductDetailViewModel","상품 정보를 가져오는 중 오류 발생")
        ApiResponse.error<List<ProductResponse>>(
            "상품 정보를 가져오는 중 오류가 발생했습니다."
        )
    }

    fun searchText_clear(){
        search_text.value = ""
    }

    fun search_tag_clear(){
        search_tags.clear()
        Prefs.searchList = search_tags
        isSearchTagsEmpty.value = true
        navigator?.listUpdate()
    }

    fun change_save_mode(){
        save_mode = !save_mode
        Prefs.saveMode = save_mode

        mode_text.value = if (save_mode){
            "자동저장 끄기"
        }else{
            "자동저장 켜기"
       }
    }

    fun finish(){
        if (isBeforeSearch.value == false){
            isBeforeSearch.value = true
            search_text.value = ""
        }else{
            navigator?.finishActivity()
        }
        //clearTasksAndStartActivity<MainActivity>()
    }

    override fun onItemClick(productId: Long?) {
        navigator?.startProductDetailActivity(productId)
    }

    fun onEditTextWatcher() : TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(search_text.value.isNullOrBlank()){
                    isTextBlank.value = true
                    isBeforeSearch.value = true
                }else{
                    isTextBlank.value = false
                }
            }
        }
    }
}