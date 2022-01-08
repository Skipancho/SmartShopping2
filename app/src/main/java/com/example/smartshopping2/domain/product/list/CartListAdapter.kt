package com.example.smartshopping2.domain.product.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.smartshopping2.R
import com.example.smartshopping2.api.response.ProductResponse
import com.example.smartshopping2.common.Prefs
import com.example.smartshopping2.databinding.CartItemBinding
import com.example.smartshopping2.domain.list.CartListViewModel
import com.example.smartshopping2.domain.list.Cart_item

class CartListAdapter(
    private val list: ArrayList<Cart_item>,
    private val context : Context?,
    private val priceCalculator: PriceCalculator
) : RecyclerView.Adapter<CartListAdapter.CartListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        val binding : ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.cart_item,
            parent,
            false
        )

        return CartListViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        val item = list[position]

        val binding = holder.binding as CartItemBinding
        binding.item = item

        binding.apply {
            amountTv.text = "${item.amount} 개"
            deleteBtn.setOnClickListener { _ ->
                list.remove(item)
                Prefs.cartList = list
                notifyDataSetChanged()
                priceCalculator.price_cal()
                priceCalculator.product_check()
            }
        }
    }

    override fun getItemCount() = list.size

    class CartListViewHolder(
        val binding : ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root)
}