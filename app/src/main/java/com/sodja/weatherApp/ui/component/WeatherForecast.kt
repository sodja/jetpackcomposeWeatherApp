package com.sodja.weatherApp.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sodja.weatherApp.R
import com.sodja.weatherApp.ui.WeatherState
import com.sodja.weatherApp.viewmodel.WeatherViewModel

@Composable
fun WeatherForecast(
    state: WeatherState,
    weatherViewModel: WeatherViewModel,
    onClickToDetailScreen: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.day_title),
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        state.weatherInfo?.forecast?.forecastday?.let { data ->
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                content = {
                    items(data) { forecastday ->
                        DayForecastdayDisplay(
                            weatherViewModel = weatherViewModel,
                            forecastday = forecastday,
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            onClickNew = onClickToDetailScreen


                        )
                    }
                })
        }
    }
}