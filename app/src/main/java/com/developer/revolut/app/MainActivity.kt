package com.developer.revolut.app

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.revolut.R
import com.developer.revolut.app.adapter.RateAdapter
import com.developer.revolut.app.entities.NavigationEvent
import com.developer.revolut.app.viewmodel.RatesViewModel
import com.developer.revolut.databinding.ActivityMainBinding
import com.developer.revolut.domain.entities.ConversionRateModel

// I could use a Fragment instead an activity but in this example there is no many benefits to have a Fragment
class MainActivity : BaseMvvmActivity<RatesViewModel>() {
    override val viewModelType = RatesViewModel::class.java
    private val adapter = RateAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.ratesList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
        }
        viewModel.navigationEvent.observe(this, Observer {
            processNavigationEvent(it)
        })
    }

    private fun processNavigationEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.UpdateItemsEvent -> updateItems(event.rateList)
            is NavigationEvent.ToastEvent -> displayToast(event.message)
        }

    }

    private fun displayToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateItems(rateList: List<ConversionRateModel>) {
        adapter.setItems(rateList)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchLatestRates()
    }
}
