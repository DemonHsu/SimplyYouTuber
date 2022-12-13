package com.icgen.movieapp.data.common


import com.icgen.movieapp.core.model.Movie
import com.icgen.movieapp.data.model.MovieData
import com.icgen.movieapp.data.model.MoviesData
import java.util.UUID

object TestHelper {

    fun makeMovies(count: Int): MoviesData {
        val movies = mutableListOf<MovieData>()
        repeat(count) { movies.add(makeMovieData()) }
        return MoviesData("token", movies)
    }

    fun makeMovieData() =
        MovieData(
            id =  UUID.randomUUID().toString(),
            videoTitle = UUID.randomUUID().toString(),
            posterPath = UUID.randomUUID().toString(),
            ownerTitle = UUID.randomUUID().toString(),
            ownerPosterPath = UUID.randomUUID().toString(),
            uploadDate = UUID.randomUUID().toString()
        )
}
