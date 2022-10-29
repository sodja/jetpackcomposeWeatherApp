package com.sodja.weatherApp.domain.repository

import com.sodja.weatherApp.commons.Resource
import com.sodja.weatherApp.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getAllData(): Flow<Resource<List<Weather>>>
    suspend fun getWeatherByCity(city: String): Flow<Resource<Weather>>
    suspend fun getAllFavoriteWeather(): Flow<Resource<List<Weather>>>
    suspend fun getFavoriteWeather(city: String): Weather?
    suspend fun addWeatherInFavorite(city: String, isFavorite: Boolean): Flow<Resource<Int>>
    suspend fun removeWeatherInFavorite(city: String, isFavorite: Boolean): Flow<Resource<Int>>
}