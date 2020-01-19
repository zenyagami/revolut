package com.developer.revolut.app.di

import com.developer.revolut.data.net.BaseUrlProvider
import com.developer.revolut.data.net.BaseUrlProviderImpl
import com.developer.revolut.data.net.RestApi
import com.developer.revolut.data.net.RestApiImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providesRestApi(restApiImpl: RestApiImpl): RestApi = restApiImpl

    @Provides
    @Singleton
    fun providesBaseUrlProvider(baseUrlProviderImpl: BaseUrlProviderImpl): BaseUrlProvider = baseUrlProviderImpl
}