package com.sodja.weatherApp.commons

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}