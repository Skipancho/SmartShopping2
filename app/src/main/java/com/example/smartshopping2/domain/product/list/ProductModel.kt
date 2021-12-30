package com.example.smartshopping2.domain.product.list

data class ProductModel(
    val id : Long,
    val name : String,
    val description : String,
    val price : String,
    val status : String,
    val sellerId : Long,
    val imagePaths : List<String>
)
