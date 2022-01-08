package com.example.smartshopping2.domain.product.search

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
import com.example.smartshopping2.databinding.ReviewItemBinding
import com.example.smartshopping2.databinding.SearchingTagItemBinding
import com.example.smartshopping2.domain.product.review.ReviewAdapter

class SearchTagAdapter(
    private val list : ArrayList<String>,
    private val context: Context,
    private val searchViewModel: SearchViewModel
) : RecyclerView.Adapter<SearchTagAdapter.SearchTagViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTagViewHolder {
        val binding : ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.searching_tag_item,
            parent,
            false
        )
        return SearchTagViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: SearchTagViewHolder, position: Int) {
        val item = list[position]

        val binding = holder.binding as SearchingTagItemBinding
        binding.item = item

        binding.apply {
            tagText.setOnClickListener { _ ->
                searchViewModel.search_text.value = item
            }
            deleteBtn.setOnClickListener { _ ->
                list.remove(item)
                searchViewModel.isSearchTagsEmpty.value = list.isNullOrEmpty()
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount() = list.size

    class SearchTagViewHolder(
        val binding : ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root)
}