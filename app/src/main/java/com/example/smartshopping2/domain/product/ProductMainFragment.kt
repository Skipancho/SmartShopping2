package com.example.smartshopping2.domain.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartshopping2.R
import com.example.smartshopping2.api.SmartShoppingApi
import com.example.smartshopping2.databinding.FragmentProductMainBinding
import com.example.smartshopping2.domain.product.list.ProductListAdapter
import com.example.smartshopping2.domain.product.list.ProductListPagedAdapter
import kotlinx.coroutines.*
import splitties.toast.toast
import kotlin.IllegalStateException

class ProductMainFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)
            .get(ProductMainViewModel::class.java)
    }

    private val adapter = ProductListAdapter(context)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentProductMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_main, container,false)

        binding.viewModel = viewModel

        binding.productsRcv.layoutManager = GridLayoutManager(context,2)
        binding.productsRcv.adapter = adapter

        loadProducts()

        return binding.root
    }

    fun loadProducts() = GlobalScope.launch {
        val response =
            SmartShoppingApi.instance.getProducts(Long.MAX_VALUE,null,"next",null)

        val products = response.data
    }


}