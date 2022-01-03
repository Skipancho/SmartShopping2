package com.example.smartshopping2.domain.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.smartshopping2.common.Prefs
import com.example.smartshopping2.domain.product.list.PriceCalculator
import java.lang.ref.WeakReference
import java.text.NumberFormat

class CheckListViewModel(app : Application) : AndroidViewModel(app), PriceCalculator {
    var navigatorRef : WeakReference<ListFragNavigator>? = null
    private val navigator = navigatorRef?.get()

    var check_list = Prefs.checkList
    val total_price_text = MutableLiveData<String>("0 원")

    override fun price_cal(){
        var sum = 0
        val list = check_list
        for (item in list){
            sum += item.price
        }
        println("sum : $sum")
        val commaSeparatedPrice =
            NumberFormat.getNumberInstance().format(sum)
        println("commaSeparatedPrice : $commaSeparatedPrice")
        updateView("$commaSeparatedPrice 원")
    }

    private fun updateView(str : String){
        total_price_text.value = str
    }
}