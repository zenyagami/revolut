package com.developer.revolut.domain.util

interface CurrencyHelper {
    fun getCurrencyCountryName(currencyCode: String): String
}