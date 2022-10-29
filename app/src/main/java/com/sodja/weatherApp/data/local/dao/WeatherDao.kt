package com.sodja.weatherApp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sodja.weatherApp.data.local.entity.WeatherEntity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM WEATHER_ENTITY")
    fun getAll(): List<WeatherEntity>

    @Query("SELECT * FROM WEATHER_ENTITY WHERE id = :id")
    fun loadById(id: Int): WeatherEntity


    @Query("SELECT * FROM WEATHER_ENTITY WHERE city = :city AND isFavorite = 1")
    fun loadFavoriteByCity(city: String): WeatherEntity?

    @Query("SELECT weather FROM WEATHER_ENTITY WHERE isFavorite = 1")
    fun getAllFavorite(): List<WeatherEntity>

    @Query("UPDATE WEATHER_ENTITY SET isFavorite = :isFavorite WHERE city = :city")
    fun updateWeatherInFavorites(city: String, isFavorite: Boolean): Int


    @Query("UPDATE WEATHER_ENTITY SET weather = :weather AND isFavorite = 1 WHERE city = :city")
    fun updateWeatherInFavorites(weather: String, city: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherEntity: WeatherEntity)

    @Query("DELETE  FROM WEATHER_ENTITY")
    fun delete()

}