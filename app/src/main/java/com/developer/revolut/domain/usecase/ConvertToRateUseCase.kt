package com.developer.revolut.domain.usecase

import com.developer.revolut.app.util.SchedulersProvider
import com.developer.revolut.app.util.round
import com.developer.revolut.domain.entities.ConversionRateModel
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ConvertToRateUseCase @Inject constructor(private val schedulersProvider: SchedulersProvider) {

    fun run(currentList: List<ConversionRateModel>,
            currentCurrency: String,
            basePrice: Double,
            ratesCache: Map<String, Double>): Single<List<ConversionRateModel>> {
        val baseConversionRate = ratesCache[currentCurrency] ?: 1.0
        return Single.just(currentList)
                .flatMapObservable { Observable.fromIterable(it) }
                .map {
                    val newPrice: Double = (basePrice * (ratesCache[it.currencyCode]
                            ?: 1.0)) / baseConversionRate
                    it.copy(price = newPrice.round(4))
                }.toList()
                .subscribeOn(schedulersProvider.io())
    }
}