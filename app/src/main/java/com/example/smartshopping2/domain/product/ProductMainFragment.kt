package com.example.smartshopping2.domain.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.smartshopping2.R
import com.example.smartshopping2.databinding.FragmentProductMainBinding
import kotlin.IllegalStateException

class ProductMainFragment : Fragment() {

    val categoryId get() = arguments?.getInt("categoryId")
        ?: throw IllegalStateException("categoryId 없음")

    val title get() = arguments?.getString("title")
        ?: throw IllegalStateException("title 없음")

    private val viewModel by lazy {
        ViewModelProvider(this)
            .get(ProductMainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentProductMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_main, container,false)

        binding.viewModel = viewModel

        return binding.root
    }

    companion object{
        fun newInstance(categoryId : Int, title : String) =
            ProductMainFragment().apply {
                arguments = Bundle().also {
                    it.putInt("categoryId", categoryId)
                    it.putString("title", title)
                }
            }
    }
}