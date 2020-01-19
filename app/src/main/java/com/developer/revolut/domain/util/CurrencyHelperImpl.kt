package com.developer.revolut.domain.util

import java.util.*
import javax.inject.Inject

class CurrencyHelperImpl @Inject internal constructor() : CurrencyHelper {
    override fun getCurrencyCountryName(currencyCode: String): String = Currency.getInstance(currencyCode).displayName


}