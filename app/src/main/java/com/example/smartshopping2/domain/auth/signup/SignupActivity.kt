package com.example.smartshopping2.domain.auth.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.smartshopping2.R
import com.example.smartshopping2.databinding.ActivitySigninBinding
import com.example.smartshopping2.databinding.ActivitySignupBinding
import com.example.smartshopping2.domain.auth.signin.SigninViewModel
import java.lang.ref.WeakReference

class SignupActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)
            .get(SignupViewModel::class.java)
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivitySignupBinding>(
            this,
            R.layout.activity_signup
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}