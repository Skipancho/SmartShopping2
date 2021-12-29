package com.example.smartshopping2.domain.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import com.example.smartshopping2.api.response.ProductResponse
import com.example.smartshopping2.domain.product.list.ProductDataSource
import com.example.smartshopping2.domain.product.list.ProductListPagedAdapter
import kotlinx.coroutines.launch
import splitties.toast.toast

class ProductMainViewModel(app:Application)
    : AndroidViewModel(app)
{
    fun startSearchProductActivity(){
        //todo. startSearchProductActivity
        toast("hello word")
    }
}