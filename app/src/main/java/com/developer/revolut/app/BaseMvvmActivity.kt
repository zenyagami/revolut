package com.developer.revolut.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * I created this class only to show the possibility to extend the Injection of the view Model to more Activities
 * in anonymous way
 */
abstract class BaseMvvmActivity<VM : ViewModel> : AppCompatActivity() {
    protected abstract val viewModelType: Class<VM>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[viewModelType]
        super.onCreate(savedInstanceState)
    }
}
