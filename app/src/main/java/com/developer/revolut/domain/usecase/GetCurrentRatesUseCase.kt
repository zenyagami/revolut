package com.developer.revolut.domain.usecase

import android.util.Log
import com.developer.revolut.app.util.SchedulersProvider
import com.developer.revolut.data.net.RestApi
import io.reactivex.Scheduler
import javax.inject.Inject

class GetCurrentRatesUseCase @Inject constructor(private val restApi: RestApi,
                                                 private val scheduler: SchedulersProvider) {

    fun run() {
        restApi.getLatestRates()
                .subscribeOn(scheduler.io())
                .subscribe ({
                    val  test = it
                    if(test ==null)
                    {}

                },{
                    it.printStackTrace()
                })
    }
}