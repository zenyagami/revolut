package com.developer.revolut.data.net

import javax.inject.Inject

class BaseUrlProviderImpl @Inject internal constructor() : BaseUrlProvider {
    override fun getRatesBaseUrl(): String = BASE_URL

    companion object {
        private const val BASE_URL = "https://revolut.duckdns.org/"
    }
}