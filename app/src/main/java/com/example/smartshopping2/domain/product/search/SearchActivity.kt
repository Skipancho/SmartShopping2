package com.example.smartshopping2.domain.product.search

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartshopping2.R
import com.example.smartshopping2.common.Prefs
import com.example.smartshopping2.databinding.ActivitySearchBinding
import com.example.smartshopping2.domain.product.detail.ProductDetailActivity
import com.example.smartshopping2.domain.product.list.ProductListAdapter
import splitties.activities.start
import java.lang.ref.WeakReference

class SearchActivity : AppCompatActivity() , SearchNavigator{

    private val viewModel by lazy {
        ViewModelProvider(this)
            .get(SearchViewModel::class.java).also {
                it.navigatorRef = WeakReference(this)
            }
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivitySearchBinding>(
            this,
            R.layout.activity_search
        )
    }

    private lateinit var adapter : ProductListAdapter
    private lateinit var tag_adapter : SearchTagAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initRecyclerView()
    }

    private fun initRecyclerView(){
        tag_adapter = SearchTagAdapter(viewModel.search_tags, this, viewModel)
        binding.searchTagRv.layoutManager = LinearLayoutManager(this)
        binding.searchTagRv.adapter = tag_adapter

        adapter = ProductListAdapter(this, viewModel.search_products, viewModel)
        binding.searchProductRv.layoutManager = GridLayoutManager(this, 2)
        binding.searchProductRv.adapter = adapter
    }

    override fun finishActivity() {
        finish()
        overridePendingTransition(R.anim.anim_none_move,R.anim.anim_right_out)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun listUpdate() {
        adapter.notifyDataSetChanged()
        tag_adapter.notifyDataSetChanged()
    }

    override fun startProductDetailActivity(productId: Long?) {
        start<ProductDetailActivity>{
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(ProductDetailActivity.PRODUCT_ID, productId)
        }
    }
}