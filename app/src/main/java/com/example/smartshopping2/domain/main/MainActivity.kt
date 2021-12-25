package com.example.smartshopping2.domain.main

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.smartshopping2.R
import com.example.smartshopping2.databinding.ActivityMainBinding
import com.example.smartshopping2.domain.mypage.MyPageActivity
import splitties.activities.start
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() , MainNavigator{

    private val viewModel by lazy {
        ViewModelProvider(this)
            .get(MainViewModel::class.java).also {
                it.navigatorRef = WeakReference(this)
            }
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun startMyPage() {
        start<MyPageActivity>()
        overridePendingTransition(R.anim.anim_right_in,R.anim.anim_none_move)
    }
}