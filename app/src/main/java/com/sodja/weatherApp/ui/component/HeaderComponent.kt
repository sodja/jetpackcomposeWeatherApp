package com.sodja.weatherApp.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HeaderComponent(onClickToBack: () -> Unit, title: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = Modifier.size(45.dp),
            onClick = { onClickToBack.invoke() }) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "",
                tint = Color.White
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = title).uppercase(),
                fontSize = 16.sp,
                fontWeight = FontWeight.W700,
                color = Color.White
            )
        }
    }
}