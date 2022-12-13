package com.icgen.movieapp.remote

import com.icgen.movieapp.remote.dto.VideosDto
import com.icgen.movieapp.remote.service.ApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeApiDataSourceImplTest {

    private val service: ApiService = mockk(relaxed = true)
    private val homeApiDataSourceImpl = HomeApiDataSourceImpl(service)

    @Test
    fun `should call service getVideos() once when getVideos() is called`() {
        return runTest {
            // Arrange
            coEvery { service.getVideos("snippet,contentDetails,status", "UU0C-w0YjGpqDXGB8IHb662A", "test", 20 )  } returns makeVideosDto()

            // Act
            homeApiDataSourceImpl.getVideos()

            // Assert
            coVerify(exactly = 1) { service.getVideos("snippet,contentDetails,status", "UU0C-w0YjGpqDXGB8IHb662A", "test", 20 ) }
        }
    }

    private fun makeVideosDto() =
        VideosDto("", emptyList())
}
