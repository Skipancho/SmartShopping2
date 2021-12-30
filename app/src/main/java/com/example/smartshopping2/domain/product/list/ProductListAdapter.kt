package com.example.smartshopping2.domain.product.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartshopping2.App
import com.example.smartshopping2.R
import com.example.smartshopping2.api.response.ProductResponse
import com.example.smartshopping2.databinding.ProductItemBinding

class ProductListAdapter(
    private val context: Context?,
    private val products : List<ProductModel>
    ) :
RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListViewHolder {
        val binding : ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.product_item,
            parent,
            false
        )

        return ProductListViewHolder(binding)
    }

    override fun getItemCount() = products.size

    class ProductListViewHolder(val binding : ViewDataBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val product = products[position]

        val binding = holder.binding as ProductItemBinding
        binding.product = product
        Glide.with(binding.image)
            .load("${App.API_HOST}${product.imagePaths.firstOrNull()}")
            .centerCrop()
            .into(binding.image)
    }
}