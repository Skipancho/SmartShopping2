package com.example.smartshopping2.domain.main

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_DENIED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.smartshopping2.R
import com.example.smartshopping2.databinding.ActivityMainBinding
import com.example.smartshopping2.domain.mypage.MyPageActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import splitties.activities.start
import splitties.toast.toast
import java.lang.ref.WeakReference
import java.util.jar.Manifest

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

        initNavigation()
        OnCheckPermission()
    }

    fun OnCheckPermission(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.CAMERA)){
                toast("앱 실행을 위해서는 권한을 설정해야 합니다.")
            }
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CAMERA),
                0x0000001
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            0x0000001 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    toast("앱 실행을 위한 권한 설정 완료")
                }
                else{
                    toast("권한 설정 취소")
                }
            }
        }
    }

    private fun initNavigation(){
        NavigationUI.setupWithNavController(findViewById<BottomNavigationView>(R.id.bottom_nav),findNavController(R.id.nav_host))
    }

    override fun startMyPage() {
        start<MyPageActivity>()
        overridePendingTransition(R.anim.anim_right_in,R.anim.anim_none_move)
    }
}