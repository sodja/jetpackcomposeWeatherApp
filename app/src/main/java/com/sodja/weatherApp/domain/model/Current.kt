package com.sodja.weatherApp.domain.model

import com.google.gson.annotations.SerializedName

data class Current(
    val cloud: Int,
    val condition: Condition,
    val humidity: Int,
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Int,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("is_day")
    val isDay: Int,
    @SerializedName("precip_mm")
    val precipMm: Double,
    @SerializedName("pressure_in")
    val pressureIn: Double,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("temp_f")
    val tempF: Double,
    @SerializedName("vis_km")
    val visKm: Double,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("wind_mph")
    val windMph: Double
)