package com.plcoding.daggerhiltcourse.util

import com.plcoding.daggerhiltcourse.data.model.Notification

interface AlarmScheduler {
    fun schedule(item: Notification)
    fun cancel(item: Notification)
}