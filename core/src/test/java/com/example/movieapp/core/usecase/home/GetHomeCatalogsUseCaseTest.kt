package com.example.movieapp.core.usecase.home

import com.example.movieapp.core.common.Dispatcher
import com.example.movieapp.core.common.TestHelper
import com.example.movieapp.core.repository.HomeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
class GetHomeCatalogsUseCaseTest {

    private val repository: HomeRepository = mockk(relaxed = true)
    private val testDispatcher = Dispatcher(
        io = UnconfinedTestDispatcher(),
        main = UnconfinedTestDispatcher(),
        default = UnconfinedTestDispatcher(),
    )

    private val useCase = GetHomeCatalogsUseCase(repository, testDispatcher)

    @Test
    fun `should call getVideos when executed`() {
        return runTest(testDispatcher.io) {
            // Act
            useCase()

            // Assert
            coVerify(exactly = 1) { repository.getVideos() }
        }
    }

    @Test
    fun `should return success with correct data when executed with success`() {
        return runTest(testDispatcher.io) {
            // Arrange
            val movies = TestHelper.makeMovies(2)

            coEvery { repository.getVideos() } returns movies


            val expected = Result.success(
                GetHomeCatalogsUseCase.Output(
                    movies,
                )
            )

            // Act
            val result = useCase()

            // Assert
            Assert.assertEquals(expected, result)
        }
    }

    @Test
    fun `should return failure when executed with exception`() {
        return runTest(testDispatcher.io) {
            // Arrange
            val exception = SocketTimeoutException()
            coEvery { repository.getVideos() } throws exception
            val expected = Result.failure<SocketTimeoutException>(exception)

            // Act
            val result = useCase()

            // Assert
            Assert.assertEquals(expected, result)
        }
    }
}
