package com.gdi.conferenceplanner.util

import com.gdi.conferenceplanner.data.model.Notification

interface AlarmScheduler {
    fun schedule(item: Notification)
    fun cancel(item: Notification)
}