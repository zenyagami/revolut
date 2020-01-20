package com.developer.revolut.app.di

import com.developer.revolut.domain.util.CurrencyHelper
import com.developer.revolut.domain.util.CurrencyHelperImpl
import com.developer.revolut.domain.util.RateCacheManager
import com.developer.revolut.domain.util.RateCacheManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CurrencyModule {
    @Provides
    @Singleton
    fun providesCurrencyHelper(currencyHelper: CurrencyHelperImpl): CurrencyHelper = currencyHelper

    @Provides
    @Singleton
    fun providesRateCacheManager(rateCacheManagerImpl: RateCacheManagerImpl): RateCacheManager = rateCacheManagerImpl
}