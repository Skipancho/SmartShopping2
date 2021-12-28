package com.example.smartshopping2.domain.product.list

import androidx.paging.PageKeyedDataSource
import com.example.smartshopping2.App
import com.example.smartshopping2.api.SmartShoppingApi
import com.example.smartshopping2.api.response.ApiResponse
import com.example.smartshopping2.api.response.ProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import splitties.toast.toast

class ProductDataSource(
    private val categoryId : Int?,
    private val keyword : String? = null
) : PageKeyedDataSource<Long, ProductResponse>(){
    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, ProductResponse>
    ) {
        val response = getProducts(Long.MAX_VALUE, NEXT)
        if (response.success){
            response.data?.let {
                if(it.isNotEmpty())
                    callback.onResult(it,it.first().id, it.last().id)
            }
        }else{
            GlobalScope.launch(Dispatchers.Main) {
                showErrorMessage(response)
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, ProductResponse>
    ) {
        val response = getProducts(params.key, PREV)
        if (response.success){
            response.data?.let {
                if(it.isNotEmpty())
                    callback.onResult(it, it.first().id)
            }
        }else{
            GlobalScope.launch(Dispatchers.Main) {
                showErrorMessage(response)
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, ProductResponse>
    ) {
        val response = getProducts(params.key, NEXT)
        if (response.success){
            response.data?.let {
                if(it.isNotEmpty())
                    callback.onResult(it, it.last().id)
            }
        }else{
            GlobalScope.launch(Dispatchers.Main) {
                showErrorMessage(response)
            }
        }
    }

    private fun getProducts(id:Long, direction :String) = runBlocking {
        try {
            SmartShoppingApi.instance.getProducts(id,categoryId,direction,keyword)
        }catch (e:Exception){
            ApiResponse.error<List<ProductResponse>>(
                "알 수 없는 오류 발생"
            )
        }
    }

    private fun showErrorMessage(
        response: ApiResponse<List<ProductResponse>>
    ){
        App.instance.toast(
            response.message ?: "unknown error"
        )
    }

    companion object{
        private const val NEXT = "next"
        private const val PREV = "prev"
    }

}