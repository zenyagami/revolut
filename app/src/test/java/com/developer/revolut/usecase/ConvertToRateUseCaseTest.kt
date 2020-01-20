package com.developer.revolut.usecase

import com.developer.revolut.app.util.round
import com.developer.revolut.domain.entities.ConversionRateModel
import com.developer.revolut.domain.usecase.ConvertToRateUseCase
import com.developer.revolut.domain.usecase.GetCurrentRatesUseCase
import com.developer.revolut.domain.util.RateCacheManager
import com.developer.revolut.rx.TrampolineSchedulerProvider
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test

class ConvertToRateUseCaseTest {
    private val schedulerProvider = TrampolineSchedulerProvider()
    private val getCurrentRatesUseCase: GetCurrentRatesUseCase = mock()
    private val cacheManager: RateCacheManager = mock()
    private val convertToRateUseCase = ConvertToRateUseCase(
        schedulerProvider,
        getCurrentRatesUseCase,
        cacheManager
    )

    @Test
    fun `Test that convert rate from base currency map correctly`() {
        // Given
        val currentList = listOf(
            ConversionRateModel(currencyCode = "EUR", currencyName = "Mexico", price = 22.9),
            ConversionRateModel(currencyCode = "US", currencyName = "Usa", price = 11.3),
            ConversionRateModel(currencyCode = "CZK", currencyName = "CountryName", price = 13.4)
        )
        val currentCurrency = "MXN"
        val basePrice = 350.0
        val mockMap: MutableMap<String, Double> = HashMap()
        mockMap.apply {
            put("EUR", 3.9)
            put("MXN", 3.8)
            put("US", 2.9)
            put("CZK", 3.9)
        }
        whenever(cacheManager.getCache()).thenReturn(mockMap)
        whenever(getCurrentRatesUseCase.run()).thenReturn(Single.just(emptyList()))


        // When
        val observable = convertToRateUseCase.run(currentList, currentCurrency, basePrice).test()

        // Then
        val values = observable.values()[0]
        values.forEach {
            val newPrice: Double = (basePrice * (mockMap[it.currencyCode]
                ?: 1.0)) / mockMap[currentCurrency]!!

            Assert.assertEquals(newPrice.round(4), it.price, 0.0)
        }
        observable.dispose()
    }

    @Test
    fun `Test that convert rate from base currency map correctly and the updated api call map correctly`() {
        // Given
        val currentList = listOf(
            ConversionRateModel(currencyCode = "EUR", currencyName = "Euro", price = 22.9),
            ConversionRateModel(currencyCode = "US", currencyName = "Usa", price = 11.3),
            ConversionRateModel(currencyCode = "CZK", currencyName = "CountryName", price = 13.4)
        )
        val currentCurrency = "MXN"
        val basePrice = 350.0
        val mockMap: MutableMap<String, Double> = HashMap()
        mockMap.apply {
            put("EUR", 3.9)
            put("MXN", 3.8)
            put("US", 2.9)
            put("CZK", 3.9)
        }
        whenever(cacheManager.getCache()).thenReturn(mockMap)
        whenever(getCurrentRatesUseCase.run()).thenReturn(Single.just(currentList))
        val currentValuesHash =currentList.associateBy({ it.currencyCode }, { it.price })


        // When
        val observable = convertToRateUseCase.run(currentList, currentCurrency, basePrice).test()
        Thread.sleep(2000)
        // Then

        val values = observable.values()[1]
        values.forEach {
            val newPrice: Double = (basePrice * (currentValuesHash[it.currencyCode]
                ?: 1.0)) / mockMap[currentCurrency]!!

            Assert.assertEquals(newPrice.round(4), it.price, 0.0)
        }

        observable.dispose()
    }
}