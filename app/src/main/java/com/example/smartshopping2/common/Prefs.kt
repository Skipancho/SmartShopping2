package com.example.smartshopping2.common

import android.util.Log
import androidx.preference.PreferenceManager
import com.example.smartshopping2.App
import com.example.smartshopping2.domain.list.Cart_item
import com.google.gson.GsonBuilder
import org.json.JSONArray

object Prefs {
    private const val TOKEN = "token"
    private const val REFRESH_TOKEN = "refresh_token"
    private const val USER_NICKNAME = "user_nickname"
    private const val USER_CODE = "user_code"

    private const val USER_ID = "user_id"

    private const val CART_LIST = "cart_list"
    private const val CHECK_LIST = "check_list"

    private const val SEARCH_LIST = "search_list"

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

    var cartList: ArrayList<Cart_item>
        get() {
            val json = prefs.getString(CART_LIST, null)
            val gson = GsonBuilder().create()
            val list = ArrayList<Cart_item>()

            try {
                json?.let {
                    val jsonArray = JSONArray(it)
                    for(i in 0 until jsonArray.length()){
                        val product = gson.fromJson(
                            jsonArray.get(i).toString(),
                            Cart_item::class.java)
                        list.add(product)
                    }
                }
            }catch (e : Exception){
                Log.e("Prefs","cartList get() error")
            }

            return list
        }
        set(value : ArrayList<Cart_item>) {
            val gson = GsonBuilder().create()
            val jsonArray = JSONArray()
            for (product in value){
                val json = gson.toJson(product, Cart_item::class.java)
                jsonArray.put(json)
            }
            if (value.isNotEmpty())
                prefs.edit().putString(CART_LIST,jsonArray.toString()).apply()
            else
                prefs.edit().putString(CART_LIST,null).apply()
        }

    var checkList : ArrayList<Cart_item>
        get() {
            val json = prefs.getString(CHECK_LIST, null)
            val gson = GsonBuilder().create()
            val list = ArrayList<Cart_item>()

            try {
                json?.let {
                    val jsonArray = JSONArray(it)
                    for(i in 0 until jsonArray.length()){
                        val product = gson.fromJson(
                            jsonArray.get(i).toString(),
                            Cart_item::class.java)
                        list.add(product)
                    }
                }
            }catch (e : Exception){
                Log.e("Prefs","checkList get() error")
            }

            return list
        }
        set(value : ArrayList<Cart_item>) {
            val gson = GsonBuilder().create()
            val jsonArray = JSONArray()
            for (product in value){
                val json = gson.toJson(product, Cart_item::class.java)
                jsonArray.put(json)
            }
            if (value.isNotEmpty())
                prefs.edit().putString(CHECK_LIST,jsonArray.toString()).apply()
            else
                prefs.edit().putString(CHECK_LIST,null).apply()
        }

    var searchList : ArrayList<String>
        get() {
            val json = prefs.getString(SEARCH_LIST, null)
            val gson = GsonBuilder().create()
            val list = ArrayList<String>()

            try {
                json?.let {
                    val jsonArray = JSONArray(it)
                    for(i in 0 until jsonArray.length()){
                        val product = gson.fromJson(
                            jsonArray.get(i).toString(),
                            String::class.java)
                        list.add(product)
                    }
                }
            }catch (e : Exception){
                Log.e("Prefs","checkList get() error")
            }

            return list
        }
        set(value) {
            val gson = GsonBuilder().create()
            val jsonArray = JSONArray()
            for (product in value){
                val json = gson.toJson(product, String::class.java)
                jsonArray.put(json)
            }
            print(jsonArray.toString())
            if (value.isNotEmpty())
                prefs.edit().putString(SEARCH_LIST,jsonArray.toString()).apply()
            else
                prefs.edit().putString(SEARCH_LIST,null).apply()
        }

}