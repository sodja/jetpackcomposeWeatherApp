package com.sodja.weatherApp.ui.fragment.detail_favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sodja.weatherApp.R
import com.sodja.weatherApp.ui.component.HeaderComponent
import com.sodja.weatherApp.ui.component.WeatherCard
import com.sodja.weatherApp.ui.component.WeatherForecast
import com.sodja.weatherApp.ui.theme.BackGround
import com.sodja.weatherApp.ui.theme.DeepBlue
import com.sodja.weatherApp.viewmodel.WeatherViewModel

@Composable
fun DetailFavoriteFragment(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel,
    onClickToBack: () -> Unit = {},
    onClickToDetailScreen: (Int) -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackGround),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BackGround)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HeaderComponent(onClickToBack, R.string.favorite_title)

            WeatherCard(
                state = weatherViewModel.currentState,
                unite = weatherViewModel.temperatureUnit,
                backgroundColor = DeepBlue,
                weatherViewModel = weatherViewModel
            )
            Spacer(modifier = Modifier.height(16.dp))
            WeatherForecast(
                state = weatherViewModel.currentState,
                weatherViewModel = weatherViewModel,
                onClickToDetailScreen = onClickToDetailScreen
            )
        }
    }

}