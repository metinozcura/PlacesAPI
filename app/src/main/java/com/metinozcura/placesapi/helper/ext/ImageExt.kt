package com.metinozcura.placesapi.helper.ext

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.metinozcura.placesapi.R

fun AppCompatImageView.setImage(url: String?) {
    if (url.isNullOrEmpty())
        return setImageResource(R.mipmap.ic_launcher)

    Glide.with(context)
        .load(url)
        .placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher)
        .into(this)
}