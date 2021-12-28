package com.example.smartshopping2.api.response

data class ProductResponse(
    val id : Long,
    val name : String,
    val description : String,
    val price : Int,
    val status : String,
    val sellerId : Long,
    val imagePaths : List<String>
)