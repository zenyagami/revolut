package com.developer.revolut.domain.usecase

import com.developer.revolut.app.util.SchedulersProvider
import com.developer.revolut.app.util.round
import com.developer.revolut.data.net.RestApi
import com.developer.revolut.domain.entities.ConversionRateModel
import com.developer.revolut.domain.util.CurrencyHelper
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GetCurrentRatesUseCase @Inject constructor(private val restApi: RestApi,
                                                 private val scheduler: SchedulersProvider,
                                                 private val convertToRateUseCase: ConvertToRateUseCase,
                                                 private val currencyHelper: CurrencyHelper) {

    fun run(): Single<List<ConversionRateModel>> {
        return restApi.getLatestRates(DEFAULT_CURRENCY)
                .flatMapObservable { Observable.fromIterable(it) }
                .map {
                    ConversionRateModel(currencyCode = it.currencyCode,
                            price = it.value.round(4),
                            currencyName = currencyHelper.getCurrencyCountryName(it.currencyCode)) //I'm assuming the country code it's valid ISO
                }.toList()
                .subscribeOn(scheduler.io())
    }

    companion object {
        private const val DEFAULT_CURRENCY = "EUR"
    }
}