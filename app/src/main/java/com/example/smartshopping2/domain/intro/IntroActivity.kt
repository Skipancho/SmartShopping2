package com.example.smartshopping2.domain.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smartshopping2.R
import com.example.smartshopping2.domain.auth.signin.SigninActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import splitties.activities.start

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        GlobalScope.launch {
            delay(1500)
            start<SigninActivity>()
            finish()
        }
    }
}