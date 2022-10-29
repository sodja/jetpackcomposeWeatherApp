package com.sodja.weatherApp.data.repository

import com.google.gson.Gson
import com.sodja.weatherApp.commons.Resource
import com.sodja.weatherApp.data.local.dao.WeatherDao
import com.sodja.weatherApp.data.local.entity.WeatherEntity
import com.sodja.weatherApp.data.remote.WeatherApi
import com.sodja.weatherApp.domain.model.Weather
import com.sodja.weatherApp.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val dao: WeatherDao
) : WeatherRepository {

    override suspend fun getAllData() = flow {
        try {
            emit(Resource.Loading)
            val gson = Gson()
            val response = dao.getAll().map { gson.fromJson(it.weather, Weather::class.java) }
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }.flowOn(Dispatchers.IO)


    override suspend fun getWeatherByCity(city: String): Flow<Resource<Weather>> = flow {
        try {
            emit(Resource.Loading)
            val responseResearch = weatherApi.getWeatherData(city, days = "7")
            val savedWeatherData = insertWeatherDataInLocalDB(city, responseResearch)
            emit(Resource.Success(savedWeatherData))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }.flowOn(Dispatchers.IO)

    private fun insertWeatherDataInLocalDB(city: String, responseResearch: Weather): Weather {
        val gson = Gson()
        val jsonString = gson.toJson(responseResearch)
        dao.loadFavoriteByCity(city.trim())?.let {
            dao.updateWeatherInFavorites(jsonString, city)
            return responseResearch
        } ?: kotlin.run {
            val entity =
                WeatherEntity(
                    null,
                    weather = jsonString,
                    city = responseResearch.location.name,
                    humidity = responseResearch.current.humidity,
                    tempC = responseResearch.current.tempC,
                    tempF = responseResearch.current.tempF,
                    visKm = responseResearch.current.visKm,
                    windKph = responseResearch.current.windKph,
                    date = responseResearch.current.lastUpdated
                )
            dao.insert(entity)
            return gson.fromJson(entity.weather, Weather::class.java)
        }
    }


    override suspend fun getAllFavoriteWeather(): Flow<Resource<List<Weather>>> = flow {
        try {
            emit(Resource.Loading)
            val gson = Gson()
            val response =
                dao.getAllFavorite().map { gson.fromJson(it.weather, Weather::class.java) }
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getFavoriteWeather(city: String): Weather? {
        val gson = Gson()
        dao.loadFavoriteByCity(city.trim())?.let {
            return gson.fromJson(it.weather, Weather::class.java)
        } ?: kotlin.run {
            return null
        }
    }


    override suspend fun addWeatherInFavorite(
        city: String,
        isFavorite: Boolean
    ): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading)
            val response = dao.updateWeatherInFavorites(city, isFavorite)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun removeWeatherInFavorite(
        city: String,
        isFavorite: Boolean
    ): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading)
            val response = dao.updateWeatherInFavorites(city, isFavorite)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}