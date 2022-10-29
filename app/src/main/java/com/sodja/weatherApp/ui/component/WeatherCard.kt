package com.sodja.weatherApp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
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
import com.sodja.weatherApp.R
import com.sodja.weatherApp.ui.WeatherState
import com.sodja.weatherApp.ui.theme.StartColor
import com.sodja.weatherApp.ui.theme.StartNone
import com.sodja.weatherApp.viewmodel.WeatherViewModel
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun WeatherCard(
    state: WeatherState,
    weatherViewModel: WeatherViewModel,
    unite: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    val city = state.weatherInfo?.location?.name
    state.weatherInfo?.current?.let { data ->
        val imagePainter = rememberAsyncImagePainter(
            model = "https:${data.condition.icon}",
            error = painterResource(id = R.drawable.ic_launcher_background),
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = city!!,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = data.lastUpdated.format(
                                DateTimeFormatter.ofPattern("HH:mm")
                            ),
                            color = Color.White
                        )
                    }
                    if (weatherViewModel.isFavorite) {
                        IconButton(
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.CenterVertically),
                            onClick = {
                                if (city != null) {
                                    weatherViewModel.removeFavorite(city)
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Star,
                                "contentDescription",
                                tint = StartColor,
                            )
                        }
                    } else {
                        IconButton(
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.CenterVertically),
                            onClick = {
                                if (city != null) {
                                    weatherViewModel.addFavorite(city)
                                }
                            }
                        ) {
                            Icon(
                                Icons.Outlined.Star,
                                "contentDescription",
                                tint = StartNone,
                            )
                        }
                    }

                }


                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = imagePainter,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (unite == "C") {
                    Text(
                        text = "${data.tempC}°C",
                        fontSize = 50.sp,
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "${data.tempF} F",
                        fontSize = 50.sp,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = data.condition.text,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataDisplay(
                        value = data.pressureIn.roundToInt(),
                        unit = "hpa",
                        icon = ImageVector.vectorResource(id = com.sodja.weatherApp.R.drawable.ic_pressure),
                        iconTint = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                    WeatherDataDisplay(
                        value = data.humidity,
                        unit = "%",
                        icon = ImageVector.vectorResource(id = com.sodja.weatherApp.R.drawable.ic_drop),
                        iconTint = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                    WeatherDataDisplay(
                        value = data.windKph.roundToInt(),
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