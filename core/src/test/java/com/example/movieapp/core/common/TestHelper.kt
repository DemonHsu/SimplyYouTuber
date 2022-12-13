package com.example.movieapp.core.common


import com.icgen.movieapp.core.model.Movie
import java.util.UUID

object TestHelper {

    fun makeMovies(count: Int): List<Movie> {
        val movies = mutableListOf<Movie>()
        repeat(count) { movies.add(makeMovie()) }
        return movies
    }

    private fun makeMovie() = Movie(
        id = UUID.randomUUID().toString(),
        videoTitle = UUID.randomUUID().toString(),
        posterPath = UUID.randomUUID().toString(),
        ownerTitle = UUID.randomUUID().toString(),
        ownerPosterPath = UUID.randomUUID().toString(),
        uploadDate = UUID.randomUUID().toString(),
        overview = UUID.randomUUID().toString(),
    )
}
