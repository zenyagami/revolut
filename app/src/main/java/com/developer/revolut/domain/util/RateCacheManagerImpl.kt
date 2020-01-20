package com.developer.revolut.domain.util

import com.developer.revolut.domain.entities.ConversionRateModel
import javax.inject.Inject

class RateCacheManagerImpl @Inject constructor() : RateCacheManager {
    private var cache: Map<String, Double> = HashMap()
    override fun updateCache(newItems: List<ConversionRateModel>) {
        //offline and fast convertion when user change edit the currency
        cache = newItems.associateBy({ it.currencyCode }, { it.price })
    }

    override fun getCache(): Map<String, Double> = cache
}