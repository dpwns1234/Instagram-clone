package com.instagram.ui.common

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.icu.text.RelativeDateTimeFormatter
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.instagram.R
import com.instagram.ui.home.HomeAdapter
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
fun userAndComment(view: TextView, nickname: String?, comment: String?, ) {
    val spannableString = SpannableString("$nickname $comment")

    if(nickname != null) {
        spannableString.setSpan(StyleSpan(Typeface.BOLD),
            0,
            nickname.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }


    view.text = spannableString
}

//@BindingAdapter("nickname", "introduce", "viewMore")
//fun postIntroduce(view: TextView, nickname: String?, comment: String?, homeViewHolder: HomeAdapter.HomeViewHolder) {
//    val spannableString = SpannableString("$nickname $comment")
//
//    if(nickname != null) {
//        spannableString.setSpan(StyleSpan(Typeface.BOLD),
//            0,
//            nickname.length,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//    }
//
//    spannableString.setSpan(object : ClickableSpan() {
//        override fun onClick(p0: View) {
//            homeViewHolder.setButtonComment()
//        }
//
//    },0,1,2)
//    view.text = spannableString
//}