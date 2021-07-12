package com.aokur.bitcointicker.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.aokur.bitcointicker.R

@BindingAdapter("loadImageFromUrl")
fun ImageView.loadImage(imageUrl: String) {
    this.load(imageUrl)
}

@BindingAdapter("setArrowBackground")
fun ImageView.setBackground(number: Double) {
    this.setBackgroundResource(if (number > 0) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down)
}

@BindingAdapter("setCoinPriceBackground")
fun TextView.setCoinPriceBackground(number: Double) {
    this.setBackgroundResource(if (number > 0) R.drawable.coin_price_up_bg else R.drawable.coin_price_up_down)
}