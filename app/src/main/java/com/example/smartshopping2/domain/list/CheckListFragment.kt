package com.example.smartshopping2.domain.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartshopping2.R
import com.example.smartshopping2.databinding.FragmentCartListBinding
import com.example.smartshopping2.databinding.FragmentCheckListBinding
import com.example.smartshopping2.domain.product.list.CartListAdapter
import java.lang.ref.WeakReference

class CheckListFragment : Fragment(), ListFragNavigator {

    private val viewModel by lazy {
        ViewModelProvider(this).get(CheckListViewModel::class.java)
            .also {
                it.navigatorRef = WeakReference(this)
            }
    }

    private lateinit var binding : FragmentCheckListBinding
    private lateinit var adapter : CartListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_check_list,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        adapter = CartListAdapter(viewModel.check_list,activity,viewModel)
        binding.checkRv.adapter = adapter
        binding.checkRv.layoutManager = LinearLayoutManager(context)

        viewModel.price_cal()

        return binding.root
    }
}