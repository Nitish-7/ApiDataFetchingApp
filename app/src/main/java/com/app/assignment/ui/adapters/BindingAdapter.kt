package com.app.assignment.ui.adapters

import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


object BindingAdapter {
    @BindingAdapter("imageUrl", "placeholder", requireAll = false)
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: String?, placeholder: Int?) {
        if (!imageUrl.isNullOrEmpty()) {
            val request = Glide.with(view.context).load(imageUrl)
            placeholder?.let { request.placeholder(it) }
            request.into(view)
        }
    }

    @BindingAdapter("strikeThrough")
    @JvmStatic
    fun strikeThrough(textView: TextView, strikeThrough: Boolean) {
        if (strikeThrough) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}