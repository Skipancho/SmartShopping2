package com.example.smartshopping2.domain.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartshopping2.R
import com.example.smartshopping2.api.response.ProductResponse
import com.example.smartshopping2.common.Prefs
import com.example.smartshopping2.databinding.FragmentCartListBinding
import com.example.smartshopping2.domain.product.list.CartListAdapter
import java.lang.ref.WeakReference

class CartListFragment : Fragment() , ListFragNavigator{

    private val viewModel by lazy {
        ViewModelProvider(this).get(CartListViewModel::class.java)
            .also {
                it.navigatorRef = WeakReference(this)
            }
    }

    private lateinit var binding : FragmentCartListBinding
    private lateinit var adapter : CartListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart_list,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        adapter = CartListAdapter(viewModel.cart_list,activity,viewModel)
        binding.cartRv.adapter = adapter
        binding.cartRv.layoutManager = LinearLayoutManager(context)

        viewModel.price_cal()
        viewModel.product_check()

        return binding.root
    }
}