package com.sodja.weatherApp.commons

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.sodja.weatherApp.MainActivity
import com.sodja.weatherApp.R
import com.sodja.weatherApp.commons.Constants.NOTIFICATION_ID
import com.sodja.weatherApp.commons.Utils.getCityName
import com.sodja.weatherApp.data.remote.WeatherApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var weatherApi: WeatherApi

    @Inject
    lateinit var locationTracker: LocationTracker

    /**
     * sends notification when receives alarm
     * and then reschedule the reminder again
     * */
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        CoroutineScope(Dispatchers.IO).launch {
            locationTracker.getCurrentLocation()?.let { location ->
                val city = getCityName(location.latitude, location.longitude, context)
                val weather = weatherApi.getWeatherData(city, "1")
                notificationManager.sendReminderNotification(
                    applicationContext = context,
                    channelId = context.getString(R.string.reminders_notification_channel_id),
                    weather.current.tempC.toString()
                )
            }
        }


        ReminderManager.startReminder(context.applicationContext)
    }
}

fun NotificationManager.sendReminderNotification(
    applicationContext: Context,
    channelId: String,
    temps: String
) {
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        1,
        contentIntent,
        FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
    )
    val builder = NotificationCompat.Builder(applicationContext, channelId)
        .setContentTitle(applicationContext.getString(R.string.title_notification_reminder))
        .setContentText(applicationContext.getString(R.string.description_notification_reminder))
        .setSmallIcon(R.drawable.ic_cloudy)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(applicationContext.getString(R.string.description_notification_reminder) + temps.toInt() + "°C")
        )
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}

