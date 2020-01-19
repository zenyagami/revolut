package com.developer.revolut.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developer.revolut.app.entities.NavigationEvent
import com.developer.revolut.app.util.SchedulersProvider
import com.developer.revolut.domain.usecase.GetCurrentRatesUseCase
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RatesViewModel @Inject constructor(private val getCurrentRatesUseCase: GetCurrentRatesUseCase,
                                         private val schedulersProvider: SchedulersProvider) : ViewModel() {
    val navigationEvent = MutableLiveData<NavigationEvent>()
    private var disposable: Disposable? = null

    fun fetchLatestRates(newPrice: Double = 1.0, currency: String = BASE_CURRENCY) {
        disposable?.dispose()
        disposable = getCurrentRatesUseCase.run(currency, newPrice)
                .toFlowable()
                .delay(1, TimeUnit.SECONDS)
                .repeat()
                .subscribeOn(schedulersProvider.io())
                .subscribe({
                    navigationEvent.postValue(NavigationEvent.UpdateItemsEvent(it))
                }, {
                    //TODO add proper error logging/handling
                    it.printStackTrace()
                    navigationEvent.postValue(NavigationEvent.ToastEvent(it.message ?: ""))
                })

    }

    override fun onCleared() {
        // free resources :D
        disposable?.dispose()
        super.onCleared()
    }

    companion object {
        private const val BASE_CURRENCY = "EUR"
    }
}