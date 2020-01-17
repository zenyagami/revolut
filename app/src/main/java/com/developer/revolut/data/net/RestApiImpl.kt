package com.developer.revolut.data.net

import com.developer.revolut.data.entities.ConversionDto
import com.google.gson.Gson
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RestApiImpl @Inject internal constructor() : RestApi {
    private var service: RevolutService

    init {
        val retrofit = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        this.service = retrofit.create(RevolutService::class.java)
    }

    override fun getLatestRates(): Single<ConversionDto> = service.getCurrentRates(BASE_CURRENCY)

    companion object {
        private const val BASE_URL = "https://revolut.duckdns.org/"
        // I will leave this as a constant but this can be passed from the whole query for several different currencies ISO
        private const val BASE_CURRENCY = "EUR"
    }

}