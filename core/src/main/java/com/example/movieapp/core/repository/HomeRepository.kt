package com.example.movieapp.core.repository

import com.icgen.movieapp.core.model.Movie

interface HomeRepository {
    suspend fun getVideos(): List<Movie>
}
