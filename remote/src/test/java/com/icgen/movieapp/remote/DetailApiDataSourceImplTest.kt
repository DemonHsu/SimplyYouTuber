package com.icgen.movieapp.remote


import com.icgen.movieapp.remote.service.ApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailApiDataSourceImplTest {

    private val service: ApiService = mockk(relaxed = true)
    private val detailApiDataSourceImpl = DetailApiDataSourceImpl(service)

    /*
    @Test
    fun `should call service getMovieDetail() once when getMovieDetail() is called`() {
        return runTest {

            // Arrange
            val id = 0
            coEvery { service.getMovieDetail(id) } returns makeDetailDto()

            // Act
            detailApiDataSourceImpl.getMovieDetail(id)

            // Assert
            coVerify(exactly = 1) { service.getMovieDetail(id) }
        }
    }

    private fun makeMovieApiDto() =
        MovieApiDto(0, emptyList(), 0, 0)

     */
}
