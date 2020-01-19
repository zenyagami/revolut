package com.developer.revolut.usecase

import com.developer.revolut.data.entities.RatesDto
import com.developer.revolut.data.net.RestApi
import com.developer.revolut.domain.entities.ConversionRateModel
import com.developer.revolut.domain.usecase.GetCurrentRatesUseCase
import com.developer.revolut.domain.util.CurrencyHelper
import com.developer.revolut.rx.TrampolineSchedulerProvider
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Test

class GetCurrentRatesUseCaseTest {
    private val restApi: RestApi = mock()
    private val schedulersProvider: TrampolineSchedulerProvider = TrampolineSchedulerProvider()
    private val currencyHelper: CurrencyHelper = mock()
    private val useCaseTest = GetCurrentRatesUseCase(restApi = restApi,
            scheduler = schedulersProvider,
            currencyHelper = currencyHelper)

    @Test
    fun `Api should return the correct values`() {
        // Given
        val currency = "EUR"
        val basePrice = 1.0
        val expectedRespponse = listOf(ConversionRateModel(currencyCode = "MXN", currencyName = "CountryName", price = 22.9),
                ConversionRateModel(currencyCode = "US", currencyName = "CountryName", price = 11.3),
                ConversionRateModel(currencyCode = "CZK", currencyName = "CountryName", price = 13.4))
        val apiResponse = listOf(RatesDto(currencyCode = "MXN", value = 22.9),
                RatesDto(currencyCode = "US", value = 11.3),
                RatesDto(currencyCode = "CZK", value = 13.4))

        whenever(currencyHelper.getCurrencyCountryName(any())).thenReturn("CountryName")
        whenever(restApi.getLatestRates(currency)).thenReturn(Single.just(apiResponse))


        // When
        val observable = useCaseTest.run(currency, basePrice).test()
        observable.awaitTerminalEvent()

        // Then
        observable.assertValue(expectedRespponse)
    }

    @Test
    fun `Api should return the correct values with different basePrice`() {
        // Given
        val currency = "EUR"
        val basePrice = 10.0
        val expectedRespponse = listOf(ConversionRateModel(currencyCode = "MXN", currencyName = "CountryName", price = 229.0),
                ConversionRateModel(currencyCode = "US", currencyName = "CountryName", price = 113.0),
                ConversionRateModel(currencyCode = "CZK", currencyName = "CountryName", price = 134.0))
        val apiResponse = listOf(RatesDto(currencyCode = "MXN", value = 22.9),
                RatesDto(currencyCode = "US", value = 11.3),
                RatesDto(currencyCode = "CZK", value = 13.4))

        whenever(currencyHelper.getCurrencyCountryName(any())).thenReturn("CountryName")
        whenever(restApi.getLatestRates(currency)).thenReturn(Single.just(apiResponse))


        // When
        val observable = useCaseTest.run(currency, basePrice).test()
        observable.awaitTerminalEvent()

        // Then
        observable.assertValue(expectedRespponse)
    }
}