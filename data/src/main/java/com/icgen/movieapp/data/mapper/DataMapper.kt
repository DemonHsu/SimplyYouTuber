package com.icgen.movieapp.data.mapper

import com.example.movieapp.core.model.Detail
import com.icgen.movieapp.core.model.Movie
import com.icgen.movieapp.data.model.DetailData
import com.icgen.movieapp.data.model.MovieData

fun MovieData.toCoreModel() =
    Movie(id, videoTitle, posterPath, ownerTitle, ownerPosterPath, uploadDate, overview)

fun DetailData.toCoreModel() =
    Detail(
        id,
        title,
        overview,
        releaseDate
    )


