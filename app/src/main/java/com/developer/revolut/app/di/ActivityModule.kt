package com.developer.revolut.app.di

import com.developer.revolut.app.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainactivity(): MainActivity
}