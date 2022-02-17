package com.example.smartshopping2.domain.classify


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.smartshopping2.R
import com.example.smartshopping2.databinding.FragmentCheckListBinding
import com.example.smartshopping2.databinding.FragmentClassifyBinding
import com.example.smartshopping2.domain.list.CheckListViewModel
import com.example.smartshopping2.domain.product.ImageLoader
import com.example.smartshopping2.domain.product.detail.ProductDetailActivity
import splitties.activities.start
import splitties.resources.int
import java.lang.ref.WeakReference
import java.util.jar.Manifest

class ClassifyFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(ClassifyViewModel::class.java)
    }

    private lateinit var binding : FragmentClassifyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_classify,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.cameraBtn.setOnClickListener { _ ->
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            activity?.startActivityFromFragment(this,intent,101)
        }
        binding.productInquiryBtn.setOnClickListener { _->
            if(viewModel.productId != 0L)
                startProductDetail(viewModel.productId)
        }

        viewModel.initClassifier(activity,"model.tflite","labels.txt")

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        println("frag : $resultCode , $requestCode")
        if (requestCode == 101 && resultCode == Activity.RESULT_OK && data != null){
            val bitmap = data.extras?.get("data") as Bitmap
            binding.image.setImageBitmap(bitmap)
            viewModel.getResult(bitmap)
        }
    }

    fun startProductDetail(productId: Long?) {
        activity?.start<ProductDetailActivity> {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(ProductDetailActivity.PRODUCT_ID, productId)
        }
    }

}