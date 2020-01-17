package com.developer.revolut.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developer.revolut.app.entities.NavigationEvent
import com.developer.revolut.domain.usecase.GetCurrentRatesUseCase
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class RatesViewModel @Inject constructor(private val getCurrentRatesUseCase: GetCurrentRatesUseCase) : ViewModel() {
    val navigationEvent = MutableLiveData<NavigationEvent>()
    private var disposable: Disposable? = null

    fun fetchLatestRates() {
        disposable = getCurrentRatesUseCase.run()
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

}