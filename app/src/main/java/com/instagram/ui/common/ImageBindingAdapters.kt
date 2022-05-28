package com.instagram.ui.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.instagram.GlideApp
import com.instagram.R


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: Any?) {
    if (imageUrl != null) {
        GlideApp.with(view)
            .load(imageUrl)
            .into(view)
    }
}

@BindingAdapter("circleImageUrl", "isSeen")
fun loadCircleImage(view: ImageView, imageUrl: String?, isSeen: Boolean) {
    if (!imageUrl.isNullOrEmpty()) {
        GlideApp.with(view)
            .load(imageUrl)
            .circleCrop()
            .into(view)
    }

    if (isSeen) {
        view.background = ColorDrawable(Color.parseColor("#00FF0000"))
    }
}
