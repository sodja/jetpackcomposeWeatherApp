package com.sodja.weatherApp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sodja.weatherApp.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteCard(
    modifier: Modifier = Modifier,
    name: String = "",
    onClickNew: () -> Unit = {},
) {
    val favoritePainter = painterResource(id = R.drawable.ic_rainy)

    Card(
        onClick = onClickNew,
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        backgroundColor = Color(0xFF685555),
        elevation = 4.dp,
        shape = RoundedCornerShape(5.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .padding(20.dp)
            ) {
                Image(
                    painter = favoritePainter,
                    contentDescription = null,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                name.uppercase(),
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(
                    start = 35.dp,
                    end = 8.dp, top = 5.dp, bottom = 5.dp
                )
            )
        }
    }
}