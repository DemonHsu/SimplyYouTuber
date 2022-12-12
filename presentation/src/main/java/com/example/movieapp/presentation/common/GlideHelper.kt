package com.example.movieapp.presentation.common

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.movieapp.presentation.R

object GlideHelper {

    fun ImageView.setAsyncImage(context: Context, path: String?) {
        Glide.with(context)
            .load(path)
            .placeholder(R.drawable.movie_placeholder)
            .into(this)
    }

    fun ImageView.setAsyncImageCircled(context: Context, path: String?) {
        Glide.with(context)
            .load(path)
            .placeholder(R.drawable.profile_placeholder)
            .circleCrop()
            .into(this)
    }
}
