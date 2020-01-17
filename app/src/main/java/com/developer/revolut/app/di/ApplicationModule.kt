package com.developer.revolut.app.di

import android.content.Context
import com.developer.revolut.app.MainApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {
    @Provides
    fun provideContext(application: MainApplication): Context {
        return application.applicationContext
    }
}