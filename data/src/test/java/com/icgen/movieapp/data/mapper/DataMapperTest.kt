package com.icgen.movieapp.data.mapper

import com.icgen.movieapp.core.model.Movie
import com.icgen.movieapp.data.common.TestHelper.makeMovieData
import org.junit.Assert.assertEquals
import org.junit.Test

class DataMapperTest {
    @Test
    fun `should map the correct movie`() {
        // Arrange
        val movieData = makeMovieData()
        val expected = Movie(
            movieData.id,
            movieData.videoTitle,
            movieData.posterPath,
            movieData.ownerTitle,
            movieData.ownerPosterPath,
            movieData.uploadDate,
            movieData.overview,
        )

        // Act
        val test = movieData.toCoreModel()

        // Assert
        assertEquals(expected, test)
    }
}
