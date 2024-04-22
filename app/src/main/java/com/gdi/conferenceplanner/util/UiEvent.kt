package com.gdi.conferenceplanner.util

sealed class UiEvent  {
    object PopBackStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
    data class ShowDeleteDialog(val state: Boolean): UiEvent()
    data class ShowFeedbackDialog(val state: Boolean): UiEvent()
    data class ShowChangePasswordDialog(val state: Boolean): UiEvent()
}