package com.sodja.weatherApp.ui.fragment.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.sodja.weatherApp.R
import com.sodja.weatherApp.ui.component.HeaderComponent
import com.sodja.weatherApp.ui.theme.BackGround
import com.sodja.weatherApp.viewmodel.WeatherViewModel

@Composable
fun SettingFragment(
    modifier: Modifier = Modifier,
    onClickToBack: () -> Unit = {},
    weatherViewModel: WeatherViewModel
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(color = BackGround),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = BackGround)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            HeaderComponent(onClickToBack, R.string.setting_title)
            Spacer(modifier = Modifier.height(25.dp))

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.setting_message),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    color = Color.White,
                )
                TempUnitComposable(weatherViewModel)
            }
            Spacer(modifier = Modifier.height(25.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .clickable {
                        weatherViewModel.exportCSV()
                    }, contentAlignment = Alignment.TopStart
            ) {
                Text(
                    text = stringResource(id = R.string.export).uppercase(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    color = Color.White
                )
            }
        }

    }
}

@Composable
fun TempUnitComposable(weatherViewModel: WeatherViewModel) {

    var mExpanded by remember { mutableStateOf(false) }

    val tempUnit = listOf("C", "F")

    var mSelectedText by remember { mutableStateOf(weatherViewModel.temperatureUnit) }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = if (mSelectedText == "C") {
                "Celsius"
            } else {
                "Fahrenheit"
            },
            onValueChange = { mSelectedText = it },
            modifier = Modifier
                .height(60.dp)
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                },
            label = { Text("Unit", color = Color.White) },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )

        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {
            tempUnit.forEach { label ->
                DropdownMenuItem(onClick = {
                    weatherViewModel.setUnit(label)
                    mSelectedText = label
                    mExpanded = false
                }) {
                    if (label == "C") {
                        Text(text = "Celsius")
                    } else {
                        Text(text = "Fahrenheit")
                    }
                }
            }
        }
    }
}