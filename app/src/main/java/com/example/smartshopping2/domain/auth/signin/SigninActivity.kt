package com.example.smartshopping2.domain.auth.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.smartshopping2.R
import com.example.smartshopping2.common.Prefs
import com.example.smartshopping2.databinding.ActivitySigninBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import splitties.toast.toast
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

        val userId = Prefs.userId
        val password = Prefs.password

        if (userId != null && password != null){
            viewModel.auto_signin(userId, password)
        }
    }

    override fun startMainActivity() {
        TODO("Not yet implemented")
    }

    override fun startSignUpActivity() {
        TODO("Not yet implemented")
    }
}