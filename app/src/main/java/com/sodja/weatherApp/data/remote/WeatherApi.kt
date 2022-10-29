package com.sodja.weatherApp.data.remote

import com.sodja.weatherApp.domain.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast.json?key=cc21d73930294936ae1130949222010&aqi=yes&alerts=yes")
    suspend fun getWeatherData(
        @Query("q") country: String,
        @Query("days") days: String,
    ): Weather
}