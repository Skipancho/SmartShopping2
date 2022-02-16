package com.example.smartshopping2.domain.product

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.smartshopping2.api.SmartShoppingApi
import com.example.smartshopping2.api.response.ApiResponse
import com.example.smartshopping2.api.response.ProductResponse
import com.example.smartshopping2.domain.main.MainNavigator
import com.example.smartshopping2.domain.product.list.ProductListAdapter
import com.example.smartshopping2.domain.product.list.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.toast.toast
import java.lang.ref.WeakReference
import java.text.NumberFormat

class ProductMainViewModel(app:Application)
    : AndroidViewModel(app),
        ProductListAdapter.OnItemClickListenr
{
    var navigatorRef : WeakReference<ProductMainNavigator>? = null
    private val navigator get() = navigatorRef?.get()

    val products = mutableListOf<ProductModel>()

    fun loadProduct(adapter: ProductListAdapter) = GlobalScope.launch {
        products.clear()
        val response = getProducts()

        val datas = response.data

        if (!datas.isNullOrEmpty()) {
            for (data in datas){
                val soldOutString =
                    if (ProductStatus.SOLD_OUT == data.status)
                        "(품절)"
                    else ""

                val commaSeparatedPrice =
                    NumberFormat.getNumberInstance().format(data.price)
                products.add(ProductModel(data.id,
                    data.name,
                    data.description,
                    "₩$commaSeparatedPrice $soldOutString",
                    data.status,
                    data.sellerId,
                    data.imagePaths))
            }
        }

        withContext(Dispatchers.Main){
            adapter.notifyDataSetChanged()
        }
    }

    private suspend fun getProducts() = try {
        SmartShoppingApi.instance.getProducts(Long.MAX_VALUE,null,"next",null)
    }catch (e : Exception){
        Log.e("ProductDetailViewModel","상품 정보를 가져오는 중 오류 발생")
        ApiResponse.error<List<ProductResponse>>(
            "상품 정보를 가져오는 중 오류가 발생했습니다."
        )
    }

    fun startSearchProductActivity(){
        navigator?.startSearchActivity()
    }

    override fun onItemClick(productId: Long?) {
        navigator?.startProductDetail(productId)
    }
}