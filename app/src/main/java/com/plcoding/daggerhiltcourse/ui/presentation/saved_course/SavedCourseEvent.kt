package com.plcoding.daggerhiltcourse.ui.presentation.saved_course

sealed class SavedCourseEvent {
    object OnDeleteCourseClick: SavedCourseEvent()
    object OnDeleteConfirmClick: SavedCourseEvent()
    object OnDeleteDismissClick: SavedCourseEvent()
    object OnFeedbackDismissClick: SavedCourseEvent()
    object OnFeedbackSubmitClick: SavedCourseEvent()
    object OnFeedbackTextChangeClick: SavedCourseEvent()
    data class OnFeedbackRatingClick(val rating: Int): SavedCourseEvent()
    data class OnSendFeedbackClick(val rating: Int, val message: String): SavedCourseEvent()
}