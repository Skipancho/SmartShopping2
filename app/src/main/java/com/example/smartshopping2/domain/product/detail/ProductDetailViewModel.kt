package com.example.smartshopping2.domain.product.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.smartshopping2.api.SmartShoppingApi
import com.example.smartshopping2.api.response.ApiResponse
import com.example.smartshopping2.api.response.ProductResponse
import com.example.smartshopping2.common.Prefs
import com.example.smartshopping2.domain.product.ProductStatus
import kotlinx.coroutines.launch
import splitties.toast.toast
import java.lang.ref.WeakReference
import java.text.NumberFormat

class ProductDetailViewModel(app : Application) : AndroidViewModel(app) {

    var navigatorRef : WeakReference<ProductDetailNavigator>? = null
    private val navigator get() = navigatorRef?.get()

    var productId : Long? = null
    var product : ProductResponse? = null

    val productName = MutableLiveData("-")
    val description = MutableLiveData("")
    val price = MutableLiveData("-")
    val imageUrls : MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())

    val isDescription = MutableLiveData(true)

    fun loadDetail(id : Long) = viewModelScope.launch {
        try {
            val response = getProduct(id)
            if (response.success && response.data != null){
                updateViewData(response.data)
            }else{
                toast(response.message ?: "알 수 없는 오류가 발생했습니다.")
            }
        }catch (e : Exception){
            toast(e.message ?: "알 수 없는 오류가 발생했습니다.")
        }
    }

    private suspend fun getProduct(id: Long) = try {
        SmartShoppingApi.instance.getProduct(id)
    }catch (e:Exception){
        Log.e("ProductDetailViewModel","상품 정보를 가져오는 중 오류 발생")
        ApiResponse.error<ProductResponse>(
            "상품 정보를 가져오는 중 오류가 발생했습니다."
        )
    }

    private fun updateViewData(product : ProductResponse){
        val commaSeparatePrice =
            NumberFormat.getInstance().format(product.price)
        val soldOutString =
            if(ProductStatus.SOLD_OUT == product.status) "(품절)" else ""
        this.product = product
        productId = product.id
        productName.value = product.name
        description.value = product.description
        price.value = "₩${commaSeparatePrice} $soldOutString"
        imageUrls.value?.addAll(product.imagePaths)
    }

    fun addProduct_toCartList(){
        toast(product.toString())
        val cartList = Prefs.cartList
        product?.let {
            cartList.add(it)
            Prefs.cartList = cartList
        }
        navigator?.finishActivity()
    }

    fun test(){
        toast(product?.name ?: "상품 없음")
        println(product.toString())
    }
}