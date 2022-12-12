package com.example.movieapp.presentation.model

data class MovieUI(
    val id: String,
    val videoTitle: String,
    val posterPath: String?,
    val ownerTitle: String,
    val ownerPosterPath: String,
    val uploadDate: String,
)
