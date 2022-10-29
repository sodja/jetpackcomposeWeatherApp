package com.sodja.weatherApp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sodja.weatherApp.R
import com.sodja.weatherApp.domain.model.Hour
import com.sodja.weatherApp.viewmodel.WeatherViewModel
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun HourForecastdayDisplay(
    weatherHourData: Hour,
    weatherViewModel: WeatherViewModel,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
    val formattedTime = remember(weatherHourData) {
        weatherHourData.time.substring(11, 16).format(
            DateTimeFormatter.ofPattern("HH:mm")
        )
    }
    val imagePainter = rememberAsyncImagePainter(
        model = "https:${weatherHourData.condition.icon}",
        error = painterResource(id = R.drawable.ic_launcher_background),
    )
    Card(
        modifier = modifier
            .fillMaxWidth(),
        backgroundColor = Color(0xFF376374),
        elevation = 4.dp,
        shape = RoundedCornerShape(5.dp),
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formattedTime,
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(3.dp))
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            if (weatherViewModel.temperatureUnit == "C") {
                Text(
                    text = "${weatherHourData.tempC}°C",
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "${weatherHourData.tempC}F",
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            WeatherDataDisplay(
                value = weatherHourData.humidity,
                unit = "%",
                icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                iconTint = Color.White,
                textStyle = TextStyle(color = Color.White)
            )
            Spacer(modifier = Modifier.height(5.dp))
            WeatherDataDisplay(
                value = weatherHourData.visKm.roundToInt(),
                unit = "km/h",
                icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                iconTint = Color.White,
                textStyle = TextStyle(color = Color.White)
            )
        }
    }
}