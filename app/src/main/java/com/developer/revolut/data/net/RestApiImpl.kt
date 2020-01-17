package com.developer.revolut.data.net

import com.developer.revolut.data.entities.ConversionDto
import io.reactivex.Single

class RestApiImpl() : RestApi {
    override fun getLatestRates(): Single<ConversionDto> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}