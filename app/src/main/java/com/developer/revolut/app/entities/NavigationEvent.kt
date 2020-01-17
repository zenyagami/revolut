package com.developer.revolut.app.entities

import com.developer.revolut.domain.entities.ConversionRateModel

sealed class NavigationEvent {
    data class UpdateItemsEvent(val rateList: List<ConversionRateModel>) : NavigationEvent()
    data class ToastEvent(val message: String) : NavigationEvent()
}