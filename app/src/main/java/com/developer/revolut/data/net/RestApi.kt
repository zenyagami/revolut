package com.developer.revolut.data.net

import com.developer.revolut.data.entities.RatesDto
import io.reactivex.Single

interface RestApi {
    fun getLatestRates(): Single<List<RatesDto>>
}