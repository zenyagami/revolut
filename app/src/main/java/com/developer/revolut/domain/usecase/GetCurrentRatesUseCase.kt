package com.developer.revolut.domain.usecase

import com.developer.revolut.app.util.SchedulersProvider
import com.developer.revolut.data.entities.RatesDto
import com.developer.revolut.data.net.RestApi
import io.reactivex.Single
import javax.inject.Inject

class GetCurrentRatesUseCase @Inject constructor(private val restApi: RestApi,
                                                 private val scheduler: SchedulersProvider) {

    fun run(): Single<List<RatesDto>> {
        return restApi.getLatestRates()
                .subscribeOn(scheduler.io())
    }
}