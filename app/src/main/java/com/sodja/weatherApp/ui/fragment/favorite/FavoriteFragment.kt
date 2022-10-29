package com.sodja.weatherApp.ui.fragment.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sodja.weatherApp.R
import com.sodja.weatherApp.commons.Resource
import com.sodja.weatherApp.ui.component.ErrorButton
import com.sodja.weatherApp.ui.component.LoadingCircular
import com.sodja.weatherApp.ui.theme.BackGround
import com.sodja.weatherApp.viewmodel.FavoritesViewModel
import com.sodja.weatherApp.viewmodel.WeatherViewModel

@Composable
fun FavoriteFragment(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel,
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    onClickToDetailFavoriteScreen: () -> Unit = {},
    onClickToBack: () -> Unit = {}
) {

    fun launch() {
        favoritesViewModel.getFavorite()
    }

    launch()
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(color = BackGround),
        color = MaterialTheme.colors.background
    ) {
        when (val favoriteResponse = favoritesViewModel.weatherState.value) {
            is Resource.Loading -> {
                LoadingCircular(
                    modifier = Modifier.fillMaxWidth()
                )
            }
            is Resource.Success -> {
                FavoriteScreen(
                    modifier = Modifier
                        .padding(
                            horizontal = 5.dp
                        ),
                    weatherViewModel = weatherViewModel,
                    newsList = favoriteResponse.data,
                    onClickToDetailScreen = onClickToDetailFavoriteScreen,
                    onClickToBack = onClickToBack
                )
            }
            is Resource.Failure -> {
                ErrorButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.error_message),
                    onClick = {
                        launch()
                    }
                )
            }
        }
    }
}

