package com.example.smartshopping2.api.response

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ReviewResponse(
    val nickName : String,
    val productName : String,
    val score : Int,
    val reviewText : String,
    val date : Date
){
    val scoreText : String
        get() = when(score){
            5 -> "⭐️⭐️⭐️⭐️⭐️"
            4 -> "⭐️⭐️⭐️⭐️️"
            3 -> "⭐️⭐️⭐️️"
            2 -> "⭐️⭐️"
            1 -> "⭐️"
            else ->""
        }
    val dateText : String
        @SuppressLint("SimpleDateFormat")
        get() = SimpleDateFormat("yyyy.MM.dd").format(date)
}
