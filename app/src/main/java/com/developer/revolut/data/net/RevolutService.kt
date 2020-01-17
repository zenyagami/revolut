package com.developer.revolut.data.net

import com.developer.revolut.data.entities.ConversionDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RevolutService {
    @GET("latest")
    fun getCurrentRates(@Query("base") currency: String): Single<ConversionDto>
}