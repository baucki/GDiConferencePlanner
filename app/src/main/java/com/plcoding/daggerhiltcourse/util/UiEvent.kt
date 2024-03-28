package com.plcoding.daggerhiltcourse.util

sealed class UiEvent  {
    object PopBackStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
    data class ShowDialog(val state: Boolean): UiEvent()
    data class UpdateSevenDaysNotificationCheckbox(val state: Boolean): UiEvent()
    data class UpdateTwoDaysNotificationCheckbox(val state: Boolean): UiEvent()
}