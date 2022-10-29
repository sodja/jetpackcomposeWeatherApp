package com.sodja.weatherApp.commons

import android.content.Context
import android.location.Geocoder
import android.util.Log
import okio.IOException
import java.util.*

object Utils {

    fun getCityName(latitude: Double, longitude: Double, context: Context): String {
        var cityName = "not found"
        val geocoder = Geocoder(context.applicationContext, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 10)

            addresses.forEach { address ->
                if (address != null) {
                    val city = address.locality
                    if (city != null) {
                        cityName = city
                    } else {
                        Log.d("TAG", "CITY NOT FOUND")
                    }
                }
            }
        } catch (e: IOException) {
            e.stackTrace
        }
        return cityName
    }

}