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

    @Inject
    @MessageNotification
    lateinit var messageNotificationBuilder: NotificationCompat.Builder

    @Inject
    @MessageNotification
    lateinit var messageNotificationManager: NotificationManagerCompat

    override fun onReceive(context: Context, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        when (message) {
            "seven days" -> {
                sevenDaysNotificationBuilder.setContentText("Kurs sa konferencije pocinje za 7 dana")
                sevenDaysNotificationManager.notify(1, sevenDaysNotificationBuilder.build())
            }
            "seven days now" -> {
                sevenDaysNotificationBuilder.setContentText("Uspesno ste napravili notifikaciju 7 dana pred kurs")
                sevenDaysNotificationManager.notify(2, sevenDaysNotificationBuilder.build())
            }
            "two days" -> {
                twoDaysNotificationBuilder.setContentText("Kurs sa konferencije pocinje za 2 dana")
                twoDaysNotificationManager.notify(3, twoDaysNotificationBuilder.build())
            }
            "two days now" -> {
                twoDaysNotificationBuilder.setContentText("Uspesno ste napravili notifikaciju 2 dana pred kurs")
                twoDaysNotificationManager.notify(4, twoDaysNotificationBuilder.build())
            }
            "now" -> {
                messageNotificationBuilder.setContentText("Uspesno ste sacuvali kurs u moj planer")
                messageNotificationManager.notify(5, messageNotificationBuilder.build())
            }

        }
    }
}