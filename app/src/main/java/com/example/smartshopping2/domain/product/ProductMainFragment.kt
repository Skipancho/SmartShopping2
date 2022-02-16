package com.example.smartshopping2.domain.product

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smartshopping2.R
import com.example.smartshopping2.api.SmartShoppingApi
import com.example.smartshopping2.api.response.ApiResponse
import com.example.smartshopping2.api.response.ProductResponse
import com.example.smartshopping2.databinding.FragmentProductMainBinding
import com.example.smartshopping2.domain.product.detail.ProductDetailActivity
import com.example.smartshopping2.domain.product.list.ProductListAdapter
import com.example.smartshopping2.domain.product.list.ProductModel
import com.example.smartshopping2.domain.product.search.SearchActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.start
import splitties.activities.start
import java.lang.ref.WeakReference
import java.text.NumberFormat

class ProductMainFragment : Fragment(), ProductMainNavigator{

    private val viewModel by lazy {
        ViewModelProvider(this)
            .get(ProductMainViewModel::class.java).also {
                it.navigatorRef = WeakReference(this)
            }
    }

    private lateinit var adapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding : FragmentProductMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_main, container,false)

        adapter = ProductListAdapter(activity, viewModel.products, viewModel)

        binding.viewModel = viewModel

        binding.productsRcv.layoutManager = GridLayoutManager(activity,2)
        binding.productsRcv.adapter = adapter

        viewModel.loadProduct(adapter)

        return binding.root
    }


    override fun startSearchActivity() {
        val intent = Intent(activity,SearchActivity::class.java)
        startActivity(intent)
    }

    override fun startProductDetail(productId: Long?) {
        activity?.start<ProductDetailActivity> {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(ProductDetailActivity.PRODUCT_ID, productId)
        }
    }
}