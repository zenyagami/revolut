package com.developer.revolut.domain.usecase

import com.developer.revolut.app.util.SchedulersProvider
import com.developer.revolut.app.util.round
import com.developer.revolut.domain.entities.ConversionRateModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ConvertToRateUseCase @Inject constructor(private val schedulersProvider: SchedulersProvider,
                                               private val getCurrentRatesUseCase: GetCurrentRatesUseCase) {

    fun run(currentList: List<ConversionRateModel>,
            currentCurrency: String,
            basePrice: Double,
            ratesCache: Map<String, Double>): Flowable<List<ConversionRateModel>> {
        val baseConversionRate = ratesCache[currentCurrency] ?: 1.0
        val immediateUpdate = Single.just(currentList)
                .flatMapObservable { Observable.fromIterable(it) }
                .map {
                    val newPrice: Double = (basePrice * (ratesCache[it.currencyCode]
                            ?: 1.0)) / baseConversionRate
                    it.copy(price = newPrice.round(4))
                }.toList()
                .toFlowable()
                .subscribeOn(schedulersProvider.io())
        return immediateUpdate.mergeWith(getUpdater(currentList, basePrice, baseConversionRate))
    }

    private fun getUpdater(currentList: List<ConversionRateModel>,
                           newPrice: Double,
                           baseConversionRate: Double): Flowable<List<ConversionRateModel>> {
        return getCurrentRatesUseCase.run()
                .map {
                    it.associateBy({ it.currencyCode }, { it.price })
                }.map { map ->
                    val newList = ArrayList<ConversionRateModel>()
                    currentList.forEach {
                        val calculatedPrice: Double = (newPrice * (map[it.currencyCode]
                                ?: 1.0)) / baseConversionRate
                        newList.add(it.copy(price = calculatedPrice.round(4)))
                    }
                    newList.toList()
                }
                .delay(1, TimeUnit.SECONDS)
                .repeat()
                .subscribeOn(schedulersProvider.io())
    }
}