package com.developer.revolut.data.net

import com.developer.revolut.data.entities.RatesDto
import com.google.gson.Gson
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RestApiImpl @Inject constructor(baseUrlProvider: BaseUrlProvider) : RestApi {
    private var service: RevolutService

    init {
        val retrofit = Retrofit
                .Builder()
                .baseUrl(baseUrlProvider.getRatesBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        this.service = retrofit.create(RevolutService::class.java)
    }

    override fun getLatestRates(currency: String): Single<List<RatesDto>> {
        // I'm mapping here to not expose all the entities to the Domain layer, this mapping I could do it in the
        // Domain layer as well but depends of what we want to expose
        return service.getCurrentRates(currency)
                .map {
                    ArrayList<RatesDto>().apply {
                        it.rates.forEach { rates ->
                            add(RatesDto(currencyCode = rates.key,
                                    value = rates.value))
                        }
                    }
                }
    }
}