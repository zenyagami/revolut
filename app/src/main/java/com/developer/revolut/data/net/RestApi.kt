package com.developer.revolut.data.net

import com.developer.revolut.data.entities.ConversionDto
import io.reactivex.Single

interface RestApi {
    fun getLatestRates(): Single<ConversionDto>
}