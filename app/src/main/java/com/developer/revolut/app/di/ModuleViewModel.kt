package com.developer.revolut.app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.developer.revolut.app.viewmodel.RatesViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class ModuleViewModel {
    @Provides
    @Singleton
    fun providesViewModelFactory(factory: CustomViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    @IntoMap
    @ViewModelKey(RatesViewModel::class)
    fun providesRatesViewModule(ratesViewModel: RatesViewModel): ViewModel = ratesViewModel

}