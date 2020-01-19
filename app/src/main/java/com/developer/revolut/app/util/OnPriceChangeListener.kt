package com.developer.revolut.app.util

import com.developer.revolut.domain.entities.ConversionRateModel

interface OnPriceChangeListener {
    fun onPriceChanged(currentList: List<ConversionRateModel>, item: ConversionRateModel, newPrice: Double)
}