package com.example.smartshopping2.domain.product

import android.annotation.SuppressLint
import android.content.Intent
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

    private val products = ArrayList<ProductModel>()
    private lateinit var adapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding : FragmentProductMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_main, container,false)

        adapter = ProductListAdapter(activity, products,viewModel)

        binding.viewModel = viewModel

        binding.productsRcv.layoutManager = GridLayoutManager(activity,2)
        binding.productsRcv.adapter = adapter

        loadProduct()

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadProduct() = GlobalScope.launch {
        products.clear()
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