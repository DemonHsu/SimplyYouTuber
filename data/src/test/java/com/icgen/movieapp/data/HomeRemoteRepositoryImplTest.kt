package com.icgen.movieapp.data

import com.icgen.movieapp.data.common.TestHelper.makeMovies
import com.icgen.movieapp.data.source.home.HomeApiDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeRemoteRepositoryImplTest {

    private val apiDataSource: HomeApiDataSource = mockk(relaxed = true)
    private val homeRepositoryImpl = HomeRepositoryImpl(apiDataSource)

    @Test
    fun `should call apiDataSource getVideos() once when getVideos() is called`() {
        return runTest {
            // Arrange
            coEvery { apiDataSource.getVideos() } returns makeMovies(3)

            // Act
            homeRepositoryImpl.getVideos()

            // Assert
            coVerify(exactly = 1) { apiDataSource.getVideos() }
        }
    }
}
