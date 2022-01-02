package com.example.smartshopping2.api.response

import com.example.smartshopping2.domain.product.ProductStatus
import java.text.NumberFormat

class ProductResponse(
    val id : Long,
    val name : String,
    val description : String,
    val price : Int,
    val status : String,
    val sellerId : Long,
    val imagePaths : List<String>
){
    val priceText : String
        get() {
            val soldOutString =
                if (ProductStatus.SOLD_OUT == status)
                    "(품절)"
                else ""
            val commaSeparatedPrice =
                NumberFormat.getNumberInstance().format(price)
            return "₩$commaSeparatedPrice $soldOutString"
        }

    override fun toString(): String {
        return " id : $id, name : $name, description : $description, " +
                "price : $priceText, status : $status"
    }
}
