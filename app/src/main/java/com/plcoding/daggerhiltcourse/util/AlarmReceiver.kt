package com.plcoding.daggerhiltcourse.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    @SevenDaysNotification
    lateinit var sevenDaysNotificationBuilder: NotificationCompat.Builder

    @Inject
    @SevenDaysNotification
    lateinit var sevenDaysNotificationManager: NotificationManagerCompat

    @Inject
    @TwoDaysNotification
    lateinit var twoDaysNotificationBuilder: NotificationCompat.Builder

    @Inject
    @TwoDaysNotification
    lateinit var twoDaysNotificationManager: NotificationManagerCompat
    override fun onReceive(context: Context, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        if (message == "seven days") {
            sevenDaysNotificationManager.notify(1, sevenDaysNotificationBuilder.build())
        }
        else if (message == "two days") {
            twoDaysNotificationManager.notify(2, twoDaysNotificationBuilder.build())
        }
    }
}