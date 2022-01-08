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
            sum += item.price * item.amount
        }
        val commaSeparatedPrice =
            NumberFormat.getNumberInstance().format(sum)
        updateView("$commaSeparatedPrice 원")
    }

    override fun product_check() {
        val checkList = check_list
        val cartList = Prefs.cartList

        for (check_item in checkList){
            var cnt = 0
            for (cart_item in cartList){
                if (cart_item.productId == check_item.productId){
                    cnt += 1
                    cart_item.isChecked = true
                    check_item.isChecked = true
                }
            }
            if (cnt == 0){
                check_item.isChecked = false
            }
        }

        Prefs.checkList = checkList
        Prefs.cartList = cartList
    }

    private fun updateView(str : String){
        total_price_text.value = str
    }
}