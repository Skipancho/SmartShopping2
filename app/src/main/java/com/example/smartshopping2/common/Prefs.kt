package com.example.smartshopping2.common

import android.util.Log
import androidx.preference.PreferenceManager
import com.example.smartshopping2.App
import com.example.smartshopping2.api.response.ProductResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import org.json.JSONArray

object Prefs {
    private const val TOKEN = "token"
    private const val REFRESH_TOKEN = "refresh_token"
    private const val USER_NICKNAME = "user_nickname"
    private const val USER_CODE = "user_code"

    private const val USER_ID = "user_id"
    private const val USER_PW = "user_pw"

    private const val CART_LIST = "cart_list"
    private const val CHECK_LIST = "check_list"

    val prefs by lazy {
        PreferenceManager.getDefaultSharedPreferences(App.instance)
    }

    var token
        get() = prefs.getString(TOKEN, null)
        set(value) = prefs.edit().putString(TOKEN, value).apply()

    var refreshToken
        get() = prefs.getString(REFRESH_TOKEN, null)
        set(value) = prefs.edit().putString(REFRESH_TOKEN, value).apply()

    var nickName
        get() = prefs.getString(USER_NICKNAME, null)
        set(value) = prefs.edit().putString(USER_NICKNAME, value).apply()

    var userCode
        get() = prefs.getLong(USER_CODE, 0)
        set(value) = prefs.edit().putLong(USER_CODE,value).apply()

    var userId
        get() = prefs.getString(USER_ID, null)
        set(value) = prefs.edit().putString(USER_ID,value).apply()

    var password
        get() = prefs.getString(USER_PW, null)
        set(value) = prefs.edit().putString(USER_PW,value).apply()

    var cartList: ArrayList<ProductResponse>
        get() {
            val json = prefs.getString(CART_LIST, null)
            val gson = GsonBuilder().create()
            val list = ArrayList<ProductResponse>()

            try {
                json?.let {
                    val jsonArray = JSONArray(it)
                    for(i in 0 until jsonArray.length()){
                        val product = gson.fromJson(
                            jsonArray.get(i).toString(),
                            ProductResponse::class.java)
                        list.add(product)
                    }
                }
            }catch (e : Exception){
                Log.e("Prefs","cartList get() error")
            }

            return list
        }
        set(value : ArrayList<ProductResponse>) {
            val gson = GsonBuilder().create()
            val jsonArray = JSONArray()
            for (product in value){
                val json = gson.toJson(product, ProductResponse::class.java)
                jsonArray.put(json)
            }
            if (value.isNotEmpty())
                prefs.edit().putString(CART_LIST,jsonArray.toString()).apply()
            else
                prefs.edit().putString(CART_LIST,null).apply()
        }

    var checkList : ArrayList<ProductResponse>
        get() {
            val json = prefs.getString(CHECK_LIST, null)
            val gson = GsonBuilder().create()
            val list = ArrayList<ProductResponse>()

            try {
                json?.let {
                    val jsonArray = JSONArray(it)
                    for(i in 0 until jsonArray.length()){
                        val product = gson.fromJson(
                            jsonArray.get(i).toString(),
                            ProductResponse::class.java)
                        list.add(product)
                    }
                }
            }catch (e : Exception){
                Log.e("Prefs","checkList get() error")
            }

            return list
        }
        set(value : ArrayList<ProductResponse>) {
            val gson = GsonBuilder().create()
            val jsonArray = JSONArray()
            for (product in value){
                val json = gson.toJson(product, ProductResponse::class.java)
                jsonArray.put(json)
            }
            if (value.isNotEmpty())
                prefs.edit().putString(CHECK_LIST,jsonArray.toString()).apply()
            else
                prefs.edit().putString(CHECK_LIST,null).apply()
        }

}