package com.plcoding.daggerhiltcourse.util

sealed class UiEvent  {
    object PopBackStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
    data class ShowDeleteDialog(val state: Boolean): UiEvent()
    data class ShowFeedbackDialog(val state: Boolean): UiEvent()
    data class UpdateSevenDaysNotificationCheckbox(val state: Boolean): UiEvent()
    data class UpdateTwoDaysNotificationCheckbox(val state: Boolean): UiEvent()
    data class UpdateFeedbackRating(val rating: Int): UiEvent()
}