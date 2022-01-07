package com.example.smartshopping2.domain.product.review

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.smartshopping2.R
import com.example.smartshopping2.api.response.ReviewResponse
import com.example.smartshopping2.databinding.ReviewItemBinding

class ReviewAdapter(
    private val list: List<ReviewResponse>,
    private val context : Context?
) : RecyclerView.Adapter<ReviewAdapter.ReviewListViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewListViewHolder {
        val binding : ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.review_item,
            parent,
            false
        )

        return ReviewListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewListViewHolder, position: Int) {
        val item = list[position]

        val binding = holder.binding as ReviewItemBinding
        binding.review = item
    }

    override fun getItemCount() = list.size

    class ReviewListViewHolder(
        val binding : ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root)
}