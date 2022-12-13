package com.example.movieapp.presentation.mapper

import com.example.movieapp.presentation.model.MovieUI
import com.icgen.movieapp.core.model.Movie

fun Movie.toPresentationModel() =
    MovieUI(id, videoTitle, posterPath, ownerTitle, ownerPosterPath, uploadDate, overview)

fun MovieUI.toCoreModel() =
    Movie(id, videoTitle, posterPath, ownerTitle, ownerPosterPath, uploadDate, overview)
