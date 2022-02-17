package com.example.smartshopping2

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object{
        lateinit var instance : App
        const val API_HOST = "http://10.0.2.2"
        const val API_PORT = 8080
        const val TEST_HOST = "http://ctg1770.cafe24.com"
    }
}