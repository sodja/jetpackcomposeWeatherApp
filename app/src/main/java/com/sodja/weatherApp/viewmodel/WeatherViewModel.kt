package com.sodja.weatherApp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.os.Environment
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sodja.weatherApp.commons.CSVWriter
import com.sodja.weatherApp.commons.Constants.UNIT_KEY
import com.sodja.weatherApp.commons.LocationTracker
import com.sodja.weatherApp.commons.Resource
import com.sodja.weatherApp.commons.Utils.getCityName
import com.sodja.weatherApp.data.local.WeatherDatabase
import com.sodja.weatherApp.domain.model.Forecastday
import com.sodja.weatherApp.domain.model.Weather
import com.sodja.weatherApp.domain.repository.PreferencesRepository
import com.sodja.weatherApp.domain.repository.WeatherRepository
import com.sodja.weatherApp.ui.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val preferencesRepository: PreferencesRepository,
    private val db: WeatherDatabase,
    private val application: Application
) : ViewModel() {


    var currentState by mutableStateOf(WeatherState())
        private set

    var temperatureUnit by mutableStateOf("C")
    var isFavorite by mutableStateOf(false)

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                val city = getCityName(
                    location.latitude,
                    location.longitude,
                    application.applicationContext
                )
                preferencesRepository.readString(UNIT_KEY).let {
                    temperatureUnit = it
                }

                getWeatherInfos(city)
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }

    @SuppressLint("RestrictedApi")
    fun exportCSV() {
        val exportDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "/CSV"
        )// your path where you want save your file
        if (!exportDir.exists()) {
            exportDir.mkdirs()
        }

        val file = File(
            exportDir,
            "${Random.nextInt(0, 100)}weather.csv"
        )//$TABLE_NAME.csv is like user.csv or any name you want to save
        try {
            file.createNewFile()
            val csvWrite = CSVWriter(FileWriter(file))
            val curCSV = db.query(
                "SELECT * FROM WEATHER_ENTITY",
                null
            )// query for get all data of your database table
            csvWrite.writeNext(curCSV.columnNames)
            while (curCSV.moveToNext()) {
                val arrStr = arrayOfNulls<String>(curCSV.columnCount)
                for (i in 0 until curCSV.columnCount - 1) {
                    when (i) {
                        20, 22 -> {
                        }
                        else -> arrStr[i] = curCSV.getString(i)
                    }
                }
                csvWrite.writeNext(arrStr)
            }
            csvWrite.close()
            curCSV.close()
            Toast.makeText(
                application.applicationContext,
                "Exported SuccessFully",
                Toast.LENGTH_LONG
            ).show()
        } catch (sqlEx: Exception) {
            sqlEx.stackTrace
        }

    }

    fun setUnit(unit: String) {
        temperatureUnit = unit
        viewModelScope.launch {
            preferencesRepository.writeString(UNIT_KEY, unit)
        }
    }

    fun getWeatherForDetail(dayId: Int): Forecastday? {
        var forecastday: Forecastday? = null
        state.weatherInfo?.forecast?.forecastday?.forEach {
            if (it.dateEpoch == dayId) {
                forecastday = it
            }
        }
        return forecastday
    }

    private fun checkIfCityIsFavorite(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getFavoriteWeather(city)?.let {
                isFavorite = true
            } ?: kotlin.run {
                isFavorite = false
            }
        }
    }

    fun getWeatherInfos(city: String) {
        viewModelScope.launch {
            weatherRepository.getWeatherByCity(city).collect { collect ->
                when (val result = collect) {
                    is Resource.Success -> {
                        checkIfCityIsFavorite(city)
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Failure -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun setCurrentFavoritesInfos(state: Weather) {
        viewModelScope.launch {
            currentState = currentState.copy(
                weatherInfo = state,
                isLoading = false,
                error = null
            )

        }
    }


    fun addFavorite(city: String) {
        viewModelScope.launch {
            weatherRepository.addWeatherInFavorite(city, true).collect { collect ->
                when (val result = collect) {
                    is Resource.Success -> {
                        isFavorite = true
                        Toast.makeText(
                            application.applicationContext,
                            "$city is add to favorite",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Failure -> {
                        Toast.makeText(
                            application.applicationContext,
                            "Sorry, unknown error",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {}
                }
            }
        }
    }


    fun removeFavorite(city: String) {
        viewModelScope.launch {
            weatherRepository.addWeatherInFavorite(city, false).collect { collect ->
                when (val result = collect) {
                    is Resource.Success -> {
                        isFavorite = false
                        Toast.makeText(
                            application.applicationContext,
                            "$city is deleted to favorite",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Failure -> {
                        Toast.makeText(
                            application.applicationContext,
                            "Sorry, unknown error",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {}
                }
            }
        }
    }
}