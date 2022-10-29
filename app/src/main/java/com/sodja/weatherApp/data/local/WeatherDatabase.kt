package com.sodja.weatherApp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sodja.weatherApp.data.local.dao.WeatherDao
import com.sodja.weatherApp.data.local.entity.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 10)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}