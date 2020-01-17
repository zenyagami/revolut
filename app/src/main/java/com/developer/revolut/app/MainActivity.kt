package com.developer.revolut.app

import android.os.Bundle
import com.developer.revolut.R
import com.developer.revolut.app.viewmodel.RatesViewModel
import dagger.android.AndroidInjection

class MainActivity : BaseMvvmActivity<RatesViewModel>() {
    override val viewModelType = RatesViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
