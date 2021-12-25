package com.example.smartshopping2.domain.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.smartshopping2.R
import com.example.smartshopping2.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)
            .get(MyPageViewModel::class.java)
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMyPageBinding>(
            this,
            R.layout.activity_my_page
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.anim_none_move,R.anim.anim_right_out)
    }
}