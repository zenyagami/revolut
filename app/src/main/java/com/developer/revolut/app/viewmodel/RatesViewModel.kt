package com.developer.revolut.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developer.revolut.app.entities.NavigationEvent
import com.developer.revolut.app.util.SchedulersProvider
import com.developer.revolut.domain.entities.ConversionRateModel
import com.developer.revolut.domain.usecase.ConvertToRateUseCase
import com.developer.revolut.domain.usecase.GetCurrentRatesUseCase
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RatesViewModel @Inject constructor(private val getCurrentRatesUseCase: GetCurrentRatesUseCase,
                                         private val convertToRateUseCase: ConvertToRateUseCase,
                                         private val schedulersProvider: SchedulersProvider) : ViewModel() {
    val navigationEvent = MutableLiveData<NavigationEvent>()
    private var disposable: Disposable? = null
    private var ratesCache: Map<String, Double> = HashMap()

    fun fetchLatestRates() {
        disposable?.dispose()
        disposable = getCurrentRatesUseCase.run()
                .toFlowable()
                .delay(1, TimeUnit.SECONDS)
                .repeat()
                .doAfterNext {
                    saveCache(it)
                }
                .subscribeOn(schedulersProvider.io())
                .subscribe({
                    navigationEvent.postValue(NavigationEvent.UpdateItemsEvent(it))
                }, {
                    //TODO add proper error logging/handling
                    it.printStackTrace()
                    navigationEvent.postValue(NavigationEvent.ToastEvent(it.message ?: ""))
                })

    }

    fun convertToNewRate(currentList: List<ConversionRateModel>, currentCurrency: String, basePrice: Double) {
        disposable?.dispose()
        disposable = convertToRateUseCase.run(currentList, currentCurrency, basePrice, ratesCache)
                .subscribe({
                    navigationEvent.postValue(NavigationEvent.UpdateItemsEvent(it))
                }, {
                    //TODO add proper error logging/handling
                    it.printStackTrace()
                    navigationEvent.postValue(NavigationEvent.ToastEvent(it.message ?: ""))
                })
    }

    private fun saveCache(originalData: List<ConversionRateModel>) {
        if (ratesCache.isEmpty()) {
            ratesCache = originalData.associateBy({ it.currencyCode }, { it.price })
        }
    }

    override fun onCleared() {
        // free resources :D
        disposable?.dispose()
        super.onCleared()
    }

}