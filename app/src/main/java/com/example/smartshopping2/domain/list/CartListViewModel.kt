package com.example.smartshopping2.domain.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.smartshopping2.api.response.ProductResponse
import com.example.smartshopping2.common.Prefs
import com.example.smartshopping2.domain.product.list.PriceCalculator
import kotlinx.coroutines.launch
import splitties.toast.toast
import java.lang.ref.WeakReference
import java.text.NumberFormat

class CartListViewModel(app : Application) : AndroidViewModel(app), PriceCalculator {

    var navigatorRef : WeakReference<ListFragNavigator>? = null
    private val navigator = navigatorRef?.get()

    var cart_list = Prefs.cartList
    var total_price = 0
    val total_price_text = MutableLiveData<String>("0 원")


    override fun price_cal(){
        total_price = 0
        val list = cart_list
        for (item in list){
            total_price += item.price * item.amount
        }
        val commaSeparatedPrice =
            NumberFormat.getNumberInstance().format(total_price)
        updateView("$commaSeparatedPrice 원")
    }

    override fun product_check() {
        val checkList = Prefs.checkList
        val cartList = cart_list

        for (cart_item in cartList){
            var cnt = 0
            for (check_item in checkList){
                if (cart_item.productId == check_item.productId){
                    cnt += 1
                    cart_item.isChecked = true
                    check_item.isChecked = true
                }
            }
            if (cnt == 0){
                cart_item.isChecked = false
            }
        }

        Prefs.checkList = checkList
        Prefs.cartList = cartList
    }

    private fun updateView(str : String){
        total_price_text.value = str
    }

    fun test(){
        price_cal()
        toast("$total_price 원,  :: ${total_price_text.value}")
    }

}