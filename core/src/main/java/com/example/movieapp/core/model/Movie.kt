package com.icgen.movieapp.core.model

data class Movie(
    val id: String,
    val videoTitle: String,
    val posterPath: String?,
    val ownerTitle: String,
    val ownerPosterPath: String,
    val uploadDate: String,
)
