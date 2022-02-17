package com.example.smartshopping2.domain.classify

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.smartshopping2.api.SmartShoppingApi
import com.example.smartshopping2.api.TestApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.toast.toast


class ClassifyViewModel(app : Application) : AndroidViewModel(app) {

    lateinit var classifier : Classifier
    val productName = MutableLiveData("해당 상품 없음")
    var isValidateProduct = false
    var productId = 0L

    fun initClassifier(
        activity : Activity?,
        modelPath:String,
        labelPath:String
    ){
        try {
            classifier = Classifier(activity!!,modelPath, labelPath)
        }catch (e : Exception) {
            toast("classifier : init error")
        }
    }

    fun getResult(bitmap: Bitmap){
        val results = classifier.recognizeImage(bitmap)
        val recognition = results.first()
        productName.value = recognition.title
        findProduct(recognition.title)
    }

    fun productInquiry(){
        if (!isValidateProduct){
            toast("다시 촬영하세요.")
        }
    }

    private fun findProduct( id : String) = viewModelScope.launch {
        /*val response = SmartShoppingApi.instance.getProduct(id.toLong())
        val data = response.data*/
        val response = TestApi.instance.getProducts("tag",id)
        val data = response.response?.firstOrNull()

        withContext(Dispatchers.Main){
            if (data == null){
                productName.value = "해당 상품 없음"
                isValidateProduct = false
            }else{
                productName.value = data.pName
                productId = data.pCode.toLong()
                isValidateProduct = true
            }
        }
    }
}