package com.sodja.weatherApp.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sodja.weatherApp.R
import com.sodja.weatherApp.domain.model.Forecastday
import com.sodja.weatherApp.viewmodel.WeatherViewModel
import java.time.format.DateTimeFormatter

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DayForecastdayDisplay(
    forecastday: Forecastday,
    weatherViewModel: WeatherViewModel,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    onClickNew: (Int) -> Unit = {}
) {

    val formattedTime = remember(forecastday) {
        forecastday.date?.format(
            DateTimeFormatter.ofPattern("HH:mm")
        )
    }
    val imagePainter = rememberAsyncImagePainter(
        model = "https:${forecastday.day?.condition?.icon}",
        error = painterResource(id = R.drawable.ic_launcher_background),
    )
    Card(
        modifier = modifier
            .fillMaxWidth(),
        backgroundColor = Color(0xFF1D4655),
        elevation = 4.dp,
        shape = RoundedCornerShape(5.dp),
    ) {
        Column(
            modifier = modifier
                .padding(5.dp)
                .clickable(
                    enabled = true,
                    onClick = {
                        onClickNew.invoke(forecastday.dateEpoch!!)
                    }
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formattedTime!!,
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = imagePainter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(bottom = 5.dp, end = 10.dp)
                )
                Column(verticalArrangement = Arrangement.Center) {
                    if (weatherViewModel.temperatureUnit == "C") {
                        Text(
                            text = "${forecastday.day?.maxtempC}°C",
                            color = textColor,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "${forecastday.day?.mintempC}°C",
                            color = textColor,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text = "${forecastday.day?.maxtempF} F",
                            color = textColor,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = "${forecastday.day?.mintempF} F",
                            color = textColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }


        }
    }
}