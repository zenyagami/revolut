package com.developer.revolut.app.adapter

import CircleTransform
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import java.util.*

object RateBindingAdapter {
    //TODO use another resource for this,
    private const val FLAG_BASE_URL = "https://raw.githubusercontent.com/transferwise/currency-flags/master/src/flags/%s.png"

    @BindingAdapter("imageFlag")
    @JvmStatic
    fun loadFlagImage(imageView: ImageView, currencyCode: String) {
        Picasso.get()
                .load(String.format(FLAG_BASE_URL, currencyCode.toLowerCase(Locale.getDefault())))
                .transform(CircleTransform())
                .into(imageView)
    }

}