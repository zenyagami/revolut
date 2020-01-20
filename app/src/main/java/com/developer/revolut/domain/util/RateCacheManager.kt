package com.developer.revolut.domain.util

import com.developer.revolut.domain.entities.ConversionRateModel

interface RateCacheManager {
    fun updateCache(newItems: List<ConversionRateModel>)
    fun getCache(): Map<String, Double>
}