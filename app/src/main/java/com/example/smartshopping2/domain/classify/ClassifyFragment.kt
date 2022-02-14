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
import splitties.activities.start
import splitties.resources.int
import java.lang.ref.WeakReference
import java.util.jar.Manifest

class ClassifyFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(ClassifyViewModel::class.java)
    }

    private lateinit var binding : FragmentClassifyBinding

    //private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_classify,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.cameraBtn.setOnClickListener { _ ->
            binding.image.setImageBitmap(ImageLoader.imageCache.values.iterator().next())
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            //intent.putExtra("CallType", 101)
            //resultLauncher.launch(intent)
            activity?.startActivityForResult(intent,101)
        }
        /*resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == 0) {
                println("여기서 안되는가?")
                val intent = it.data
                val callType = intent?.getIntExtra("CallType", 0)
                println("calltype : $callType")
                when (callType) {
                    101 -> {
                        val bitmap = intent.extras?.get("data") as Bitmap
                        binding.image.setImageBitmap(bitmap)
                    }
                }
            }
        }*/

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == Activity.RESULT_OK && data != null){
            val bitmap = data.extras?.get("data") as Bitmap
            binding.image.setImageBitmap(bitmap)
        }
    }

}