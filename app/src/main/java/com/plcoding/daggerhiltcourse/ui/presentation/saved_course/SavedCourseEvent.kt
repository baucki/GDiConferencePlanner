package com.plcoding.daggerhiltcourse.ui.presentation.saved_course

sealed class SavedCourseEvent {
    object OnDeleteCourseClick: SavedCourseEvent()
    object OnDeleteConfirmClick: SavedCourseEvent()
    object OnDeleteDismissClick: SavedCourseEvent()
    data class OnLeaveFeedbackClick(val message: String): SavedCourseEvent()
}