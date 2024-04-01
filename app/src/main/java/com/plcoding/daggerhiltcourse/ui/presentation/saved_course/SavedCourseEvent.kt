package com.plcoding.daggerhiltcourse.ui.presentation.saved_course

sealed class SavedCourseEvent {
    object OnDeleteCourseClick: SavedCourseEvent()
    object OnDeleteConfirmClick: SavedCourseEvent()
    object OnDeleteDismissClick: SavedCourseEvent()
    object OnFeedbackDismissClick: SavedCourseEvent()
    object OnFeedbackClick: SavedCourseEvent()
    data class OnFeedbackTextChangeClick(val value: String): SavedCourseEvent()
    data class OnFeedbackRatingClick(val rating: Int): SavedCourseEvent()
    data class OnFeedbackSubmitClick(val rating: Int, val message: String): SavedCourseEvent()
}