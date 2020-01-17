package com.developer.revolut.app.di

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
}