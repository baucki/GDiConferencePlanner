package com.gdi.conferenceplanner.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.gdi.conferenceplanner.R
import com.gdi.conferenceplanner.ui.presentation.MainActivity
import com.gdi.conferenceplanner.util.MessageNotification
import com.gdi.conferenceplanner.util.SevenDaysNotification
import com.gdi.conferenceplanner.util.TwoDaysNotification
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {
    @SevenDaysNotification
    @Singleton
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        val clickIntent = Intent(context,MainActivity::class.java)
        val clickPendingIntent = PendingIntent.getActivity(
            context,
            1,
            clickIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, "Main Channel ID")
            .setContentTitle("GDi Konferencija 2024")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(0,"OPEN", clickPendingIntent)
            .setContentIntent(clickPendingIntent)
    }
    @SevenDaysNotification
    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        val channel = NotificationChannel(
            "Main Channel ID",
            "main channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
        return notificationManager
    }


    @TwoDaysNotification
    @Singleton
    @Provides
    fun provideTwoNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        val clickIntent = Intent(context,MainActivity::class.java)
        val clickPendingIntent = PendingIntent.getActivity(
            context,
            1,
            clickIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, "Second Channel ID")
            .setContentTitle("GDi Konferencija 2024")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(0,"OPEN", clickPendingIntent)
            .setContentIntent(clickPendingIntent)
    }
    @TwoDaysNotification
    @Singleton
    @Provides
    fun provideTwoNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        val channel = NotificationChannel(
            "Second Channel ID",
            "second channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
        return notificationManager
    }

    @MessageNotification
    @Singleton
    @Provides
    fun provideMessageNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        val clickIntent = Intent(context,MainActivity::class.java)
        val clickPendingIntent = PendingIntent.getActivity(
            context,
            1,
            clickIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, "Third Channel ID")
            .setContentTitle("GDi Konferencija 2024")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(0,"OPEN", clickPendingIntent)
            .setContentIntent(clickPendingIntent)
    }
    @MessageNotification
    @Singleton
    @Provides
    fun provideMessageNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        val channel = NotificationChannel(
            "Third Channel ID",
            "third channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
        return notificationManager
    }
}