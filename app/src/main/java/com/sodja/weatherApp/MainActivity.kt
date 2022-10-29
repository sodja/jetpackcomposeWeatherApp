package com.sodja.weatherApp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sodja.weatherApp.commons.Constants.DETAIL_ARG_NEWS_ID
import com.sodja.weatherApp.commons.ReminderManager
import com.sodja.weatherApp.navigation.Routes
import com.sodja.weatherApp.ui.fragment.detail.DayWeatherFragment
import com.sodja.weatherApp.ui.fragment.detail_favorite.DetailFavoriteFragment
import com.sodja.weatherApp.ui.fragment.favorite.FavoriteFragment
import com.sodja.weatherApp.ui.fragment.home.HomeFragment
import com.sodja.weatherApp.ui.fragment.setting.SettingFragment
import com.sodja.weatherApp.ui.theme.WeatherAppTheme
import com.sodja.weatherApp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            createNotificationsChannels()
            ReminderManager.startReminder(this)
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.SCHEDULE_EXACT_ALARM,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
        )
        setContent {
            WeatherAppTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Spacer(modifier = Modifier.height(5.dp))
                    WeatherAppScreen(weatherViewModel = viewModel)
                    if (viewModel.state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    viewModel.state.error?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }

    private fun createNotificationsChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getString(R.string.reminders_notification_channel_id),
                getString(R.string.reminders_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            ContextCompat.getSystemService(this, NotificationManager::class.java)
                ?.createNotificationChannel(channel)
        }
    }

}

@Composable
fun WeatherAppScreen(weatherViewModel: WeatherViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route,
    ) {
        composable(route = Routes.Home.route) {
            HomeFragment(
                state = weatherViewModel.state,
                weatherViewModel = weatherViewModel,
                onClickToSettingScreen = { ->
                    navController.navigate(
                        Routes.Setting.route
                    )
                },
                onClickToFavoriteScreen = { ->
                    navController.navigate(
                        Routes.Favorite.route
                    )
                },
                onClickToDetailScreen = { newsId ->
                    navController.navigate(
                        Routes.Detail.createRoute(newsId)
                    )
                }
            )
        }
        composable(route = Routes.Setting.route) {
            SettingFragment(
                weatherViewModel = weatherViewModel,
                onClickToBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Routes.Favorite.route) {
            FavoriteFragment(
                onClickToDetailFavoriteScreen = { ->
                    navController.navigate(
                        Routes.DetailFavorite.route
                    )
                },
                weatherViewModel = weatherViewModel,
                onClickToBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Routes.DetailFavorite.route) {
            DetailFavoriteFragment(
                onClickToBack = {
                    navController.popBackStack()
                },
                weatherViewModel = weatherViewModel,
                onClickToDetailScreen = { newsId ->
                    navController.navigate(
                        Routes.Detail.createRoute(newsId)
                    )
                }
            )
        }
        composable(
            route = Routes.Detail.route,
            arguments = listOf(
                navArgument(DETAIL_ARG_NEWS_ID) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val dayId = backStackEntry.arguments?.getInt(DETAIL_ARG_NEWS_ID)
            weatherViewModel.getWeatherForDetail(dayId!!)?.let {
                DayWeatherFragment(
                    state = it,
                    weatherViewModel = weatherViewModel,
                    onClickToBack = {
                        navController.popBackStack()
                    })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
        //Greeting("Android")
    }
}