package com.sodja.weatherApp.ui.component

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.sodja.weatherApp.ui.theme.WeatherAppTheme

@Composable
fun LoadingCircular(
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (
            loadingCircular,
        ) = createRefs()
        CircularProgressIndicator(
            modifier = Modifier
                .constrainAs(loadingCircular) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingCircularPreview() {
    WeatherAppTheme {
        LoadingCircular()
    }
}