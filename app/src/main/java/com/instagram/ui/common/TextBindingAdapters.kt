package com.instagram.ui.common

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.icu.text.RelativeDateTimeFormatter
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.instagram.R
import java.util.*


@BindingAdapter("text")
fun writeText(view: TextView, text: String) {
    view.text = text
}

@BindingAdapter("likeCount")
fun writeLikeCount(view: TextView, count: Int) {
    val setText = "좋아요 ${count}개"
    view.text = setText
}

@BindingAdapter("calculatingCount")
fun calculateCount(view: TextView, count: Int) {
    if(count >= 10000) {
        view.text = view.context.getString(R.string.calculate_count, count / 10000)
    }
    else {
        view.text = count.toString()
    }
}


@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("SimpleDateFormat")
@BindingAdapter("createdAt")
fun createdAt(view: TextView, createdTime: Long) {
    val formatter = RelativeDateTimeFormatter.getInstance(Locale("ko_KR"))
    var currentTime = (System.currentTimeMillis() - createdTime) / 1000
    val direction= RelativeDateTimeFormatter.Direction.LAST
    val relativeUnit: RelativeDateTimeFormatter.RelativeUnit

    if(currentTime < 60){
        relativeUnit = RelativeDateTimeFormatter.RelativeUnit.SECONDS
    }
    else if (currentTime >= 60 && currentTime < 60*60 ) {
        relativeUnit = RelativeDateTimeFormatter.RelativeUnit.MINUTES
        currentTime /= 60
    }
    else if (currentTime >= 60*60 && currentTime < 60*60*24){
        relativeUnit = RelativeDateTimeFormatter.RelativeUnit.HOURS
        currentTime /= (60 * 60)
    }
    else {
        relativeUnit = RelativeDateTimeFormatter.RelativeUnit.DAYS
        currentTime /= (60*60*24)
    }

    val formatTime = formatter.format(currentTime.toDouble(), direction, relativeUnit)
    Log.d("file", "format: $formatTime")
    view.text = formatTime
}

@BindingAdapter("nickname", "comment")
fun userAndComment(view: TextView, user: String?, comment: String?) {
    val spannableString = SpannableString("$user $comment")

    if(user != null) {
        spannableString.setSpan(StyleSpan(Typeface.BOLD),
            0,
            user.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    view.text = spannableString
}