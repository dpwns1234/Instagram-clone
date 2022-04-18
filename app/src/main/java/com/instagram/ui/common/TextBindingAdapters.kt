package com.instagram.ui.common

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.instagram.R


@BindingAdapter("calculatingCount")
fun calculateCount(view: TextView, count: Int) {
    if(count >= 10000) {
        view.text = view.context.getString(R.string.calculate_count, count / 10000)
    }
    else {
        view.text = count.toString()
    }
}