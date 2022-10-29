package com.sodja.weatherApp.navigation

import com.sodja.weatherApp.commons.Constants.DETAIL_ARG_NEWS_ID
import com.sodja.weatherApp.commons.Constants.DETAIL_FAVORITE_SCREEN
import com.sodja.weatherApp.commons.Constants.DETAIL_SCREEN
import com.sodja.weatherApp.commons.Constants.FAVORITE_SCREEN
import com.sodja.weatherApp.commons.Constants.LIST_SCREEN
import com.sodja.weatherApp.commons.Constants.SETTING_SCREEN

sealed class Routes(val route: String) {
    object Home : Routes(LIST_SCREEN)
    object Setting : Routes(SETTING_SCREEN)
    object Favorite : Routes(FAVORITE_SCREEN)
    object DetailFavorite : Routes(DETAIL_FAVORITE_SCREEN)
    object Detail : Routes("$DETAIL_SCREEN/{$DETAIL_ARG_NEWS_ID}") {
        fun createRoute(newId: Int) = "$DETAIL_SCREEN/$newId"
    }
}