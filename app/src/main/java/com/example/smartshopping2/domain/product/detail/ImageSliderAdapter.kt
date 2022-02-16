package com.example.smartshopping2.domain.product.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.smartshopping2.R

class ImageSliderAdapter(val context: Context) : PagerAdapter() {
    private var imageUrls : List<String> = listOf()
    private lateinit var inflater : LayoutInflater

    override fun getCount() = imageUrls.size

    override fun isViewFromObject(view: View, `object`: Any) =
        view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.invalidate()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.detail_image,container, false)
        view.findViewById<ImageView>(R.id.image).apply {
            Glide.with(this)
                .load(imageUrls[position])
                .into(this)
        }
        container.addView(view)
        return view
    }


    fun updateItems(items : MutableList<String>){
        imageUrls = items
        notifyDataSetChanged()
    }
}