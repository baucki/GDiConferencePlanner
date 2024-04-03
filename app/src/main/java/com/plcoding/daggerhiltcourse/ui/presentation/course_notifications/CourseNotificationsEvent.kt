package com.plcoding.daggerhiltcourse.ui.presentation.course_notifications

import com.plcoding.daggerhiltcourse.data.model.Notification
import com.plcoding.daggerhiltcourse.util.AlarmScheduler

sealed class CourseNotificationsEvent {
    data class OnConfirmClick(val scheduler: AlarmScheduler): CourseNotificationsEvent()
    data class OnSevenDaysNotificationClick(val isNotify: Boolean): CourseNotificationsEvent()
    data class OnTwoDaysNotificationClick(val isNotify: Boolean): CourseNotificationsEvent()
}