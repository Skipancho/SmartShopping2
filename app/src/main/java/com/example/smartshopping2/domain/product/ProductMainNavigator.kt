package com.example.smartshopping2.domain.product

interface ProductMainNavigator {
    fun startSearchActivity()
    fun startProductDetail(productId : Long?)
}