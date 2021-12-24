package com.example.smartshopping2.common

import android.app.Activity
import android.content.Intent
import com.example.smartshopping2.App
import splitties.activities.start

inline fun <reified A:Activity> clearTasksAndStartActivity(){
    App.instance.start<A>{
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}