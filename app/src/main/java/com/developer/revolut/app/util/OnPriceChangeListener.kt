package com.developer.revolut.app.util

interface OnPriceChangeListener {
    fun onPriceChanged(newPrice: String, currency: String)
}