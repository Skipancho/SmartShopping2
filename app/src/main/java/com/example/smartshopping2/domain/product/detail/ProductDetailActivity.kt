package com.example.smartshopping2.domain.product.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartshopping2.R
import com.example.smartshopping2.databinding.ActivityProductDetailBinding
import com.example.smartshopping2.domain.product.review.ReviewAdapter
import com.google.android.material.tabs.TabLayout
import java.lang.ref.WeakReference

class ProductDetailActivity : AppCompatActivity(), ProductDetailNavigator{

    private val viewModel by lazy {
        ViewModelProvider(this)
            .get(ProductDetailViewModel::class.java).also {
                it.navigatorRef = WeakReference(this)
            }
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityProductDetailBinding>(
            this,
            R.layout.activity_product_detail
        )
    }

    private lateinit var adapter : ReviewAdapter
    private lateinit var imageSliderAdapter : ImageSliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productId = intent.getLongExtra(PRODUCT_ID,-1)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initTab()
        initReviewList()
        initViewPager()

        viewModel.loadDetail(productId)
    }

    private fun initViewPager(){
        imageSliderAdapter = ImageSliderAdapter(this)
        binding.viewPager.adapter = imageSliderAdapter
    }

    private fun initReviewList(){
        adapter = ReviewAdapter(viewModel.reviews,this)

        binding.reviewLv.adapter = adapter
        binding.reviewLv.layoutManager = LinearLayoutManager(this)
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

    override fun finishActivity() {
        finish()
        overridePendingTransition(R.anim.anim_none_move,R.anim.anim_right_out)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    override fun updateDetailImage() {
        imageSliderAdapter.updateItems(viewModel.imageUrls)
    }
    companion object{
        val PRODUCT_ID = "product_id"
    }
}