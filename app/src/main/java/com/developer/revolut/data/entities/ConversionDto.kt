package com.developer.revolut.data.entities

data class ConversionDto(val base: String,
                         val date: String,
                         val rates: Map<String, Double>)