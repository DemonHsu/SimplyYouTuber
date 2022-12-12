package com.icgen.movieapp.data.model

data class MovieData(
    val id: String,
    val videoTitle: String,
    val posterPath: String?,
    val ownerTitle: String,
    val ownerPosterPath: String,
    val uploadDate: String,
)


data class MoviesData(
    val nextPageToken: String,
    val list: List<MovieData>
)