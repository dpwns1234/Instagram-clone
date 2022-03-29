package com.instagram.ui.common

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view)
            .load(imageUrl)
            .into(view)
    }
}

@BindingAdapter("imageUrlList")
fun loadImageList(view: ImageView, imageUrlList: List<String>) {
    for (url in imageUrlList) {
        Glide.with(view)
            .load(url)
            .into(view)
    }
}