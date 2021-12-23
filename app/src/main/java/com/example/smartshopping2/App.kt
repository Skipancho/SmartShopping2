package com.example.smartshopping2

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object{
        lateinit var instance : App
        const val API_HOST = "10.0.0.2"
        const val API_PORT = 8080
    }
}