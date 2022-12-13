package com.example.movieapp.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieUI(
    val id: String,
    val videoTitle: String,
    val posterPath: String?,
    val ownerTitle: String,
    val ownerPosterPath: String,
    val uploadDate: String,
    val overview: String,
) : Parcelable
