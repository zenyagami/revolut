package com.developer.revolut

import com.developer.revolut.data.entities.RatesDto
import com.developer.revolut.data.net.BaseUrlProvider
import com.developer.revolut.data.net.RestApiImpl
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RestApiTest {
    private val server = MockWebServer()
    private val baseUrlProvider: BaseUrlProvider = mock()

    @Before
    fun setup() {
        server.start()
        whenever(baseUrlProvider.getRatesBaseUrl()).thenReturn(server.url("myUrl/").toString())
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `should get a Http Response`() {
        // Given
        val testSubscriber = TestObserver.create<List<RatesDto>>()
        server.enqueue(MockResponse().setBody(getJsonResponseFromFile()))
        val currency = "EUR"
        val restApi = RestApiImpl(baseUrlProvider)

        // When
        restApi.getLatestRates(currency).subscribe(testSubscriber)
        testSubscriber.awaitTerminalEvent()

        // Then
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()
    }

    @Test
    fun `should parse Dto correctly `() {
        // Given
        val testSubscriber = TestObserver.create<List<RatesDto>>()
        server.enqueue(MockResponse().setBody(getJsonResponseFromFile()))
        val currency = "MXN"
        val restApi = RestApiImpl(baseUrlProvider)

        // When
        restApi.getLatestRates(currency).subscribe(testSubscriber)
        testSubscriber.awaitTerminalEvent()

        // Then
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()
        val data = testSubscriber.values()[0]

        Assert.assertEquals("AUD", data[0].currencyCode)
        Assert.assertEquals(0.072295, data[0].value, 0.0)
        Assert.assertEquals("BGN", data[1].currencyCode)
        Assert.assertEquals(0.087475, data[1].value, 0.0)
        Assert.assertEquals("BRL", data[2].currencyCode)
        Assert.assertEquals(0.21432, data[2].value, 0.0)
    }

    private fun getJsonResponseFromFile(): String {
        return RestApiTest::class.java.classLoader!!.getResourceAsStream("rates_response.json")
                .bufferedReader().use { it.readText() }
    }

}