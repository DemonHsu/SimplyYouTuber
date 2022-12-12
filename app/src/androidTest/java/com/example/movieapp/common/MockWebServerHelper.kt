package com.example.movieapp.common


import com.example.movieapp.common.ApiConstants.CHANNEL_RESPONSE_FILE
import com.example.movieapp.common.ApiConstants.VIDEOS_RESPONSE_FILE
import com.example.movieapp.common.FileReader.readStringFromFile
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

object MockWebServerHelper {

    fun setSuccessMockedResponse(
        mockWebServer: MockWebServer,
        responseFile: String
    ) {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(readStringFromFile(responseFile))
            }
        }
    }

    fun setSuccessMockedResponse(mockWebServer: MockWebServer, id: Int) {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when {
                    (request.path == "/channels") -> {
                        MockResponse()
                            .setResponseCode(200)
                            .setBody(readStringFromFile(CHANNEL_RESPONSE_FILE))
                    }

                    (request.path == "/playlistItems") -> {
                        MockResponse()
                            .setResponseCode(200)
                            .setBody(readStringFromFile(VIDEOS_RESPONSE_FILE))
                    }

                    else -> {
                        MockResponse().setResponseCode(404)
                    }
                }
            }
        }
    }

    fun setErrorMockedResponse(mockWebServer: MockWebServer) {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().throttleBody(1024, 35, TimeUnit.SECONDS)
            }
        }
    }

    fun serverIsDone(client: OkHttpClient): Callable<Boolean> {
        return Callable {
            client.dispatcher.runningCallsCount() == 0
        }
    }
}
