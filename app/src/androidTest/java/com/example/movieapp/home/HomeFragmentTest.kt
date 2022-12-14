package com.example.movieapp.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility.GONE
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.movieapp.common.ApiConstants.VIDEOS_RESPONSE_FILE
import com.example.movieapp.common.MockWebServerHelper.serverIsDone
import com.example.movieapp.common.MockWebServerHelper.setErrorMockedResponse
import com.example.movieapp.common.MockWebServerHelper.setSuccessMockedResponse
import com.example.movieapp.common.launchFragmentInHiltContainer
import com.example.movieapp.home.ui.HomeFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.awaitility.kotlin.await
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var client: OkHttpClient
    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        hiltRule.inject()
        mockWebServer.start(8080)
    }

    @Test
    fun shouldLaunchFragmentInContainer() = runTest {
        // Act
        launchFragmentInHiltContainer<HomeFragment>()
    }

    @Test
    fun shouldShowLoaderWhenFetchingData(): Unit = runBlocking {
        // Arrange
        setSuccessMockedResponse(mockWebServer, VIDEOS_RESPONSE_FILE)

        // Act
        launchFragmentInHiltContainer<HomeFragment>()

        // Assert
        onView(withId(R.id.homeProgress))
            .check(matches(withEffectiveVisibility(VISIBLE)))
    }

    @Test
    fun shouldHideLoaderWhenDataIsFetched(): Unit = runBlocking {
        // Arrange
        setSuccessMockedResponse(mockWebServer, VIDEOS_RESPONSE_FILE)

        // Act
        launchFragmentInHiltContainer<HomeFragment>()
        await.until(serverIsDone(client))

        // Assert
        onView(withId(R.id.homeProgress))
            .check(matches(withEffectiveVisibility(GONE)))
    }

    @Test
    fun shouldShowErrorViewWhenDataIsNotFetched(): Unit = runBlocking {
        // Arrange
        setErrorMockedResponse(mockWebServer)

        // Act
        launchFragmentInHiltContainer<HomeFragment>()
        await.until(serverIsDone(client))

        // Assert
        onView(withId(R.id.errorContent))
            .check(matches(withEffectiveVisibility(VISIBLE)))
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}
