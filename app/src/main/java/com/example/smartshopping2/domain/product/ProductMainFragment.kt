package com.example.smartshopping2.domain.product

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smartshopping2.R
import com.example.smartshopping2.api.SmartShoppingApi
import com.example.smartshopping2.databinding.FragmentProductMainBinding
import com.example.smartshopping2.domain.product.list.ProductListAdapter
import com.example.smartshopping2.domain.product.list.ProductModel
import kotlinx.coroutines.*
import java.text.NumberFormat

class ProductMainFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)
            .get(ProductMainViewModel::class.java)
    }

    private val products = ArrayList<ProductModel>()
    private lateinit var adapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding : FragmentProductMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_main, container,false)

        adapter = ProductListAdapter(activity, products)

        binding.viewModel = viewModel

        binding.productsRcv.layoutManager = GridLayoutManager(activity,2)
        binding.productsRcv.adapter = adapter

        loadImage()

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadImage() = GlobalScope.launch {
        val response =
            SmartShoppingApi.instance.getProducts(Long.MAX_VALUE,null,"next",null)

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


}