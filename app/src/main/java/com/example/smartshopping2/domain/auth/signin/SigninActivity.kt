package com.example.smartshopping2.domain.auth.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.smartshopping2.R
import com.example.smartshopping2.common.Prefs
import com.example.smartshopping2.databinding.ActivitySigninBinding
import com.example.smartshopping2.domain.auth.signup.SignupActivity
import com.example.smartshopping2.domain.main.MainActivity
import splitties.activities.start
import java.lang.ref.WeakReference

class SigninActivity : AppCompatActivity(),SigninNavigator{

    private val viewModel by lazy {
        ViewModelProvider(this)
            .get(SigninViewModel::class.java).also {
                it.navigatorRef = WeakReference(this)
            }
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivitySigninBinding>(
            this,
            R.layout.activity_signin
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        if (Prefs.token != null){
            startMainActivity()
        }
    }

    override fun startMainActivity() {
        start<MainActivity>()
        finish()
    }

    override fun startSignUpActivity() {
        start<SignupActivity>()
    }
}