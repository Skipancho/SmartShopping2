package com.example.smartshopping2.domain.list

import java.text.NumberFormat

data class Cart_item(
    val productId : Long,
    val name : String,
    val price : Int,
    var amount : Int,
    var isChecked : Boolean
){
    val priceText : String
        get() {
            val commaSeparatedPrice =
                NumberFormat.getNumberInstance().format(price)
            return "₩$commaSeparatedPrice"
        }
}
