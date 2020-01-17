package com.developer.revolut.app.viewmodel

import androidx.lifecycle.ViewModel
import com.developer.revolut.domain.usecase.GetCurrentRatesUseCase
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class RatesViewModel @Inject constructor(private val getCurrentRatesUseCase: GetCurrentRatesUseCase) : ViewModel() {
    private var disposable: Disposable? = null
    fun fetchLatestRates() {
        disposable = getCurrentRatesUseCase.run()
                .subscribe({
                    val test = it
                    if (test == null) {
                    }

                }, {
                    it.printStackTrace()
                })

    }

    override fun onCleared() {
        // free resources :D
        disposable?.dispose()
        super.onCleared()
    }

}