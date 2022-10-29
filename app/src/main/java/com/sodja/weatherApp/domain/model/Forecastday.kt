package com.sodja.weatherApp.domain.model

import com.google.gson.annotations.SerializedName

data class Forecastday(
    val date: String? = null,
    @SerializedName("date_epoch")
    val dateEpoch: Int? = 0,
    val day: Day? = null,
    val hour: List<Hour>? = null
)