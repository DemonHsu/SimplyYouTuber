package com.icgen.movieapp.remote.service

import com.google.gson.Gson
import com.icgen.movieapp.remote.dto.PlayListDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class ApiServiceTest {

    @get:Rule val mockWebServer = MockWebServer()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService by lazy {
        retrofit.create(ApiService::class.java)
    }


    @Test
    fun `should return popular movies when getPlaylist() is called`() {
        return runTest {

            // Arrange
            val movieApiDto = makePlayListDto()
            val response = Gson().toJson(movieApiDto)
            enqueueSuccessResponse(response)

            // Act
            val observer = apiService.getPlaylist("snippet,contentDetails,status", "UU0C-w0YjGpqDXGB8IHb662A", "test", 20)

            // Assert
            assertEquals(movieApiDto, observer)
            assertEquals(0, observer.items.size)
        }
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `should throw exception when server code is not 200`() {
        return runTest {
            // Arrange
            enqueueErrorResponse()

            // Act
            apiService.getPlaylist("snippet,contentDetails,status", "UU0C-w0YjGpqDXGB8IHb662A", "test", 20)
        }
    }

    @Test
    fun `should hit the correct endpoint when getPlaylist() is called`() {
        return runBlocking {
            // Arrange
            setupSuccessRequest()

            // Act
            apiService.getPlaylist("snippet,contentDetails,status", "UU0C-w0YjGpqDXGB8IHb662A", "test", 20)

            // Assert
            assertEquals("/playlistItems?part=snippet%2CcontentDetails%2Cstatus&playlistId=UU0C-w0YjGpqDXGB8IHb662A&key=test&maxResults=20", mockWebServer.takeRequest().path)
        }
    }


    private fun setupSuccessRequest() {
        val movieApiDto = makePlayListDto()
        val response = Gson().toJson(movieApiDto)
        enqueueSuccessResponse(response)
    }

    private fun enqueueSuccessResponse(response: String) {
        mockWebServer.enqueue(
            MockResponse().setBody(response)
                .setResponseCode(200)
        )
    }

    private fun enqueueErrorResponse() {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(400)
        )
    }

    private fun makePlayListDto() =
        PlayListDto("", emptyList())
}
