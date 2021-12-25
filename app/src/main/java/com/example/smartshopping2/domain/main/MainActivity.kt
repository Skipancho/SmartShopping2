package com.example.smartshopping2.domain.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.smartshopping2.R
import com.example.smartshopping2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)
            .get(MainViewModel::class.java)
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

}