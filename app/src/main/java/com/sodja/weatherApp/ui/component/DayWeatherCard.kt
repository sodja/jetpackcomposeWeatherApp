package com.sodja.weatherApp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.sodja.weatherApp.domain.model.Forecastday
import com.sodja.weatherApp.viewmodel.WeatherViewModel
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun DayWeatherCard(
    forecastday: Forecastday,
    weatherViewModel: WeatherViewModel,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    forecastday.let { data ->
        val imagePainter = rememberAsyncImagePainter(
            model = "https:${data.day?.condition?.icon}",
            error = painterResource(id = com.sodja.weatherApp.R.drawable.ic_launcher_background),
        )
        Card(
            backgroundColor = backgroundColor,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = data.date!!.format(
                        DateTimeFormatter.ofPattern("HH:mm")
                    ),
                    modifier = Modifier.align(Alignment.End),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = imagePainter,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (weatherViewModel.temperatureUnit == "C") {
                    Text(
                        text = "${data.day!!.avgtempC}°C",
                        fontSize = 50.sp,
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "${data.day!!.avgtempF} F",
                        fontSize = 50.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = data.day.condition.text,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataDisplay(
                        value = data.day.dailyChanceOfRain,
                        unit = "hpa",
                        icon = ImageVector.vectorResource(id = com.sodja.weatherApp.R.drawable.ic_pressure),
                        iconTint = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                    WeatherDataDisplay(
                        value = data.day.avghumidity.roundToInt(),
                        unit = "%",
                        icon = ImageVector.vectorResource(id = com.sodja.weatherApp.R.drawable.ic_drop),
                        iconTint = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                    WeatherDataDisplay(
                        value = data.day.avgvisKm.roundToInt(),
                        unit = "km/h",
                        icon = ImageVector.vectorResource(id = com.sodja.weatherApp.R.drawable.ic_wind),
                        iconTint = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                }
            }
        }
    }
}