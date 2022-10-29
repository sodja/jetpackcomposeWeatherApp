package com.sodja.weatherApp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sodja.weatherApp.commons.Constants.BASE_URL
import com.sodja.weatherApp.commons.Constants.DATABASE_NAME
import com.sodja.weatherApp.data.local.WeatherDatabase
import com.sodja.weatherApp.data.local.dao.WeatherDao
import com.sodja.weatherApp.data.remote.WeatherApi
import com.sodja.weatherApp.data.repository.WeatherRepositoryImpl
import com.sodja.weatherApp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModules {

    @Provides
    @Named("BASE_URL")
    fun provideBaseUrl(): String = BASE_URL


    @Provides
    fun provideRetrofit(
        @Named("BASE_URL") baseUrl: String,
    ): Retrofit {
        val client = OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideWeatherApiApi(
        retrofit: Retrofit
    ): WeatherApi = retrofit.create(WeatherApi::class.java)

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): WeatherDatabase {
        return Room.databaseBuilder(
            appContext,
            WeatherDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao =
        weatherDatabase.weatherDao()

    @Provides
    fun provideWeatherRepository(
        weatherApi: WeatherApi,
        dao: WeatherDao
    ): WeatherRepository = WeatherRepositoryImpl(weatherApi, dao)

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }
}