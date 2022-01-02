package com.example.smartshopping2.domain.product.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.smartshopping2.R
import com.example.smartshopping2.databinding.ActivityProductDetailBinding
import com.google.android.material.tabs.TabLayout

class ProductDetailActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)
            .get(ProductDetailViewModel::class.java)
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityProductDetailBinding>(
            this,
            R.layout.activity_product_detail
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productId = intent.getLongExtra(PRODUCT_ID,-1)
        viewModel.loadDetail(productId)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initTab()
    }

    private fun initTab() {
        binding.tabLayout.also {
            it.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val position = tab?.position
                    when(position){
                        0 -> viewModel.isDescription.value = true
                        else -> viewModel.isDescription.value = false
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            }
            )
        }
    }

    companion object{
        val PRODUCT_ID = "product_id"
    }
}