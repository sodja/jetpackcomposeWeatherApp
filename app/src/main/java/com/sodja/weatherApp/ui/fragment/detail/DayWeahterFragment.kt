package com.sodja.weatherApp.ui.fragment.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sodja.weatherApp.R
import com.sodja.weatherApp.domain.model.Forecastday
import com.sodja.weatherApp.ui.component.DayWeatherCard
import com.sodja.weatherApp.ui.component.DayWeatherForecast
import com.sodja.weatherApp.ui.component.HeaderComponent
import com.sodja.weatherApp.ui.theme.BackGround
import com.sodja.weatherApp.ui.theme.DeepBlue
import com.sodja.weatherApp.viewmodel.WeatherViewModel

@Composable
fun DayWeatherFragment(
    state: Forecastday,
    modifier: Modifier = Modifier,
    onClickToBack: () -> Unit = {},
    weatherViewModel: WeatherViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackGround)
            .padding(horizontal = 6.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        HeaderComponent(onClickToBack, R.string.weather_data_by_day)
        Spacer(modifier = Modifier.height(5.dp))
        DayWeatherCard(
            forecastday = state,
            weatherViewModel = weatherViewModel,
            backgroundColor = DeepBlue
        )
        Spacer(modifier = Modifier.height(16.dp))
        DayWeatherForecast(forecastday = state, weatherViewModel = weatherViewModel)
    }

}