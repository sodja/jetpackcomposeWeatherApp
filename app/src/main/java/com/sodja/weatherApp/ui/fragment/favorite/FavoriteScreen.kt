package com.sodja.weatherApp.ui.fragment.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sodja.weatherApp.R
import com.sodja.weatherApp.domain.model.Weather
import com.sodja.weatherApp.ui.component.FavoriteCard
import com.sodja.weatherApp.ui.component.HeaderComponent
import com.sodja.weatherApp.ui.theme.BackGround
import com.sodja.weatherApp.viewmodel.WeatherViewModel

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    newsList: List<Weather>?,
    onClickToDetailScreen: () -> Unit = {},
    onClickToBack: () -> Unit = {},
    weatherViewModel: WeatherViewModel,

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackGround)
    ) {

        HeaderComponent(onClickToBack, R.string.favorite_title)

        Spacer(modifier = Modifier.height(16.dp))
        newsList?.let {
            LazyColumn {
                items(newsList.size) { index ->
                    newsList[index].let { new ->
                        val name = new.location.name
                        FavoriteCard(
                            modifier = modifier
                                .padding(8.dp),
                            name = name,
                            onClickNew = {
                                weatherViewModel.setCurrentFavoritesInfos(newsList[index])
                                onClickToDetailScreen.invoke()
                            }
                        )
                    }
                }
            }
        }

    }

}
