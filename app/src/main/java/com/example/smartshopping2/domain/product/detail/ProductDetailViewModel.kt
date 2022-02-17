package com.example.smartshopping2.domain.product.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.smartshopping2.api.SmartShoppingApi
import com.example.smartshopping2.api.TestApi
import com.example.smartshopping2.api.response.*
import com.example.smartshopping2.common.Prefs
import com.example.smartshopping2.domain.list.Cart_item
import com.example.smartshopping2.domain.product.ProductStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    val imageUrls : MutableList<String> = mutableListOf()

    val isDescription = MutableLiveData(true)

    val reviews = mutableListOf<ReviewResponse>()

    fun loadDetail(id : Long) = viewModelScope.launch {
        try {
            /*val response = getProduct(id)

            if (response.success && response.data != null){
                updateViewData(response.data)
            }else{
                toast(response.message ?: "알 수 없는 오류가 발생했습니다.")
            }*/
            val response = TestApi.instance.getProducts("pCode",id.toString())
            val data = response.response?.firstOrNull()

            if (data != null){
                testViewUpdate(data)
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

    private fun testViewUpdate(testProduct : testResponse){
        val commaSeparatePrice =
            NumberFormat.getInstance().format(testProduct.price)
        val soldOutString =
            if(testProduct.amount == 0) "(품절)" else ""
        this.product = ProductResponse(testProduct.pCode.toLong(),testProduct.pName,testProduct.info,testProduct.price,"",0,
            listOf())
        productId = testProduct.pCode.toLong()
        productName.value = testProduct.pName
        description.value = testProduct.info
        price.value = "₩${commaSeparatePrice} $soldOutString"
        imageUrls.add("https://ctg1770.cafe24.com/SC/image/$productId.jpg")

        navigator?.updateDetailImage()
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
        imageUrls.addAll(product.imagePaths)

        navigator?.updateDetailImage()
        getReviews()
    }

    fun addProduct_toCartList(){
        val cartList = Prefs.cartList
        product?.let {
            val addItem = Cart_item(it.id,it.name,it.price,1,false)
            var cnt = 0
            for ( c in cartList){
                if (c.productId == addItem.productId){
                    cnt += 1
                    c.amount += 1
                }
            }
            if (cnt == 0){
                cartList.add(addItem)
            }
            Prefs.cartList = cartList
        }
        navigator?.finishActivity()
    }

    fun addProduct_toCheckList(){
        val checkList = Prefs.checkList
        product?.let {
            val addItem = Cart_item(it.id,it.name,it.price,1,false)
            var cnt = 0
            for ( c in checkList){
                if (c.productId == addItem.productId){
                    cnt += 1
                    c.amount += 1
                }
            }
            if (cnt == 0){
                checkList.add(addItem)
            }
            Prefs.checkList = checkList
        }
        navigator?.finishActivity()
    }

    fun getReviews() = viewModelScope.launch {
        println("productId : $productId")
        val response = SmartShoppingApi.instance.getReviews(null,productId)
        val datas = response.data

        if (datas != null) {
            reviews.addAll(datas)
        }

        withContext(Dispatchers.Main){
            navigator?.updateList()
        }
    }

    fun test(){
        toast(product?.name ?: "상품 없음")
        println(product.toString())
    }
}