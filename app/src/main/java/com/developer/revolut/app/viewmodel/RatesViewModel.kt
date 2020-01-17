package com.developer.revolut.app.viewmodel

import androidx.lifecycle.ViewModel
import com.developer.revolut.domain.usecase.GetCurrentRatesUseCase
import javax.inject.Inject

class RatesViewModel @Inject constructor(private val getCurrentRatesUseCase: GetCurrentRatesUseCase) : ViewModel() {

}