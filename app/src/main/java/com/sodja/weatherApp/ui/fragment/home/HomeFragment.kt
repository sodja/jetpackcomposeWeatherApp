package com.sodja.weatherApp.ui.fragment.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.sodja.weatherApp.ui.WeatherState
import com.sodja.weatherApp.ui.component.SearchView
import com.sodja.weatherApp.ui.component.WeatherCard
import com.sodja.weatherApp.ui.component.WeatherForecast
import com.sodja.weatherApp.ui.theme.BackGround
import com.sodja.weatherApp.ui.theme.DeepBlue
import com.sodja.weatherApp.ui.theme.StartColor
import com.sodja.weatherApp.viewmodel.WeatherViewModel

@Composable
fun HomeFragment(
    state: WeatherState,
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel,
    onClickToSettingScreen: () -> Unit = {},
    onClickToFavoriteScreen: () -> Unit = {},
    onClickToDetailScreen: (Int) -> Unit = {}
) {
    Scaffold {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = BackGround)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val textState = remember { mutableStateOf(TextFieldValue("")) }
            Row(
                modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SearchView(textState, weatherViewModel)
                IconButton(
                    modifier = Modifier
                        .size(50.dp)
                        .align(CenterVertically),
                    onClick = { onClickToFavoriteScreen.invoke() }
                ) {
                    Icon(
                        Icons.Default.Star,
                        "contentDescription",
                        tint = StartColor,
                        modifier = Modifier.size(30.dp)
                    )
                }
                IconButton(
                    modifier = Modifier
                        .size(50.dp)
                        .align(CenterVertically),
                    onClick = { onClickToSettingScreen.invoke() }
                ) {
                    Icon(
                        Icons.Filled.Settings,
                        "contentDescription",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                }
            }
            WeatherCard(
                state = state,
                unite = weatherViewModel.temperatureUnit,
                backgroundColor = DeepBlue,
                weatherViewModel = weatherViewModel
            )
            Spacer(modifier = Modifier.height(16.dp))
            WeatherForecast(
                state = state,
                weatherViewModel = weatherViewModel,
                onClickToDetailScreen = onClickToDetailScreen
            )
        }
    }


}