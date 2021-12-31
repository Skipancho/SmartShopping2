package com.example.smartshopping2.domain.product.search

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartshopping2.R
import com.example.smartshopping2.databinding.ActivitySearchBinding
import com.example.smartshopping2.domain.product.list.ProductListAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initRecyclerView()
    }

    private fun initRecyclerView(){
        adapter = ProductListAdapter(this, viewModel.search_products)

        binding.searchTag.layoutManager = LinearLayoutManager(this)
        binding.searchTag.adapter = adapter
    }

    override fun finishActivity() {
        finish()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun listUpdate() {
        adapter.notifyDataSetChanged()
    }
}