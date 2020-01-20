package com.developer.revolut

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.developer.revolut.app.entities.NavigationEvent
import com.developer.revolut.app.viewmodel.RatesViewModel
import com.developer.revolut.domain.entities.ConversionRateModel
import com.developer.revolut.domain.usecase.ConvertToRateUseCase
import com.developer.revolut.domain.usecase.GetCurrentRatesUseCase
import com.developer.revolut.rx.TrampolineSchedulerProvider
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.io.IOException

class RatesViewModelTest {
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    private val getCurrentRatesUseCase: GetCurrentRatesUseCase = mock()
    private val convertToRateUseCase: ConvertToRateUseCase = mock()
    private var testSchedulerProvider = TrampolineSchedulerProvider()
    private val viewModel =
        RatesViewModel(getCurrentRatesUseCase, convertToRateUseCase, testSchedulerProvider)

    @Test
    fun `test Api FetchData and send event`() {
        // Given
        val response = listOf(
            ConversionRateModel(currencyCode = "MXN", currencyName = "Mexican peso", price = 22.4),
            ConversionRateModel(currencyCode = "EUR", currencyName = "Euro", price = 22.4)
        )
        val observer: Observer<NavigationEvent> = mock()
        whenever(getCurrentRatesUseCase.run()).thenReturn(Single.just(response))
        viewModel.navigationEvent.observeForever(observer)

        // When
        viewModel.fetchLatestRates()
        Thread.sleep(2000) //TODO use testScheduler.advanceTimeBy instead Thread sleep

        // Then
        verify(observer).onChanged(NavigationEvent.UpdateItemsEvent(response))
    }

    @Test
    fun `test Api FetchData and send Error event`() {
        // Given
        val observer: Observer<NavigationEvent> = mock()
        val exception = IOException("Error loading")
        whenever(getCurrentRatesUseCase.run()).thenReturn(Single.error(exception))
        viewModel.navigationEvent.observeForever(observer)

        // When
        viewModel.fetchLatestRates()

        // Then
        verify(observer).onChanged(NavigationEvent.ToastEvent(exception.message!!))
    }

    @Test
    fun `test convert to new rate should send data event`() {
        // Given
        val response = listOf(
            ConversionRateModel(currencyCode = "MXN", currencyName = "Mexican peso", price = 22.4),
            ConversionRateModel(currencyCode = "EUR", currencyName = "Euro", price = 22.4)
        )
        val observer: Observer<NavigationEvent> = mock()
        whenever(convertToRateUseCase.run(any(), eq("EUR"), eq(2.0))).thenReturn(Flowable.just(response))
        viewModel.navigationEvent.observeForever(observer)

        // When
        viewModel.convertToNewRate(emptyList(),"EUR",2.0)


        // Then
        verify(observer).onChanged(NavigationEvent.UpdateItemsEvent(response))
    }

    @Test
    fun `test convert to new rate should notify error`() {
        // Given
        val ioException = IOException("Error")
        val observer: Observer<NavigationEvent> = mock()
        whenever(convertToRateUseCase.run(any(), eq("EUR"), eq(2.0))).thenReturn(Flowable.error(ioException))
        viewModel.navigationEvent.observeForever(observer)

        // When
        viewModel.convertToNewRate(emptyList(),"EUR",2.0)


        // Then
        verify(observer).onChanged(NavigationEvent.ToastEvent(ioException.message!!))
    }
}