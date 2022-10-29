package com.sodja.weatherApp.ui

import com.sodja.weatherApp.domain.model.Weather


data class WeatherState(
    val weatherInfo: Weather? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
