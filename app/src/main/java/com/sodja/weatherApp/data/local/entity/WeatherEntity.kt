package com.sodja.weatherApp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "weather_entity", indices = [Index(value = ["city"], unique = true)])
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val isFavorite: Boolean? = false,
    val city: String?,
    val weather: String?,
    @ColumnInfo(name = "temperature_c")
    val tempC: Double?,
    @ColumnInfo(name = "temperature_f")
    val tempF: Double?,
    @ColumnInfo(name = "vis_km")
    val visKm: Double?,
    @ColumnInfo(name = "wind_kph")
    val windKph: Double?,
    val humidity: Int?,
    val date: String?,
)