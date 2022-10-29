package com.sodja.weatherApp.domain.model

import com.google.gson.annotations.SerializedName

data class Location(
    val country: String,
    val lat: Double,
    val localtime: String,
    @SerializedName("localtime_epoch")
    val localtime_epoch: Int,
    val lon: Double,
    val name: String,
    val region: String,
    @SerializedName("tz_id")
    val tz_id: String
)