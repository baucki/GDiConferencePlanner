package com.gdi.conferenceplanner.ui.presentation.course_notifications

import com.gdi.conferenceplanner.util.AlarmScheduler

sealed class CourseNotificationsEvent {
    data class OnConfirmClick(val scheduler: AlarmScheduler): CourseNotificationsEvent()
    data class OnSevenDaysNotificationClick(val isNotify: Boolean): CourseNotificationsEvent()
    data class OnTwoDaysNotificationClick(val isNotify: Boolean): CourseNotificationsEvent()
}