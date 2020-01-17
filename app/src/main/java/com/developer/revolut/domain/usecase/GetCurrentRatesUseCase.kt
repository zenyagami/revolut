package com.developer.revolut.domain.usecase

import com.developer.revolut.app.util.SchedulersProvider
import com.developer.revolut.data.net.RestApi
import com.developer.revolut.domain.entities.ConversionRateModel
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class GetCurrentRatesUseCase @Inject constructor(private val restApi: RestApi,
                                                 private val scheduler: SchedulersProvider) {

    fun run(): Single<List<ConversionRateModel>> {
        return restApi.getLatestRates()
                .flatMapObservable { Observable.fromIterable(it) }
                .map {
                    ConversionRateModel(countryCode = it.currencyCode,
                            price = it.value,
                            countryName = Currency.getInstance(it.currencyCode).displayName) //I'm assuming the country code it's valid ISO
                }.toList()
                .subscribeOn(scheduler.io())
    }
}