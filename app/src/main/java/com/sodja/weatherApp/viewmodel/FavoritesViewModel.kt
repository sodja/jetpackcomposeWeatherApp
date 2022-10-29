package com.sodja.weatherApp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sodja.weatherApp.commons.Resource
import com.sodja.weatherApp.domain.model.Weather
import com.sodja.weatherApp.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _weatherState = mutableStateOf<Resource<List<Weather>>>(Resource.Success(null))
    val weatherState: State<Resource<List<Weather>>> = _weatherState


    fun getFavorite() {
        viewModelScope.launch {
            weatherRepository.getAllFavoriteWeather().collect { response ->
                _weatherState.value = response
            }
        }
    }
}