package com.sodja.weatherApp.domain.model

import com.google.gson.annotations.SerializedName

data class Day(
    val avghumidity: Double,
    @SerializedName("maxtemp_c")
    val maxtempC: Double,
    @SerializedName("mintemp_c")
    val mintempC: Double,
    @SerializedName("avgtemp_c")
    val avgtempC: Double,
    @SerializedName("maxtemp_f")
    val maxtempF: Double,
    @SerializedName("mintemp_f")
    val mintempF: Double,
    @SerializedName("avgtemp_f")
    val avgtempF: Double,
    @SerializedName("avgvis_km")
    val avgvisKm: Double,
    val condition: Condition,
    @SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Int,
    @SerializedName("totalprecip_mm")
    val totalprecipMm: Double,

    )