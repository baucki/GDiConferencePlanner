package com.gdi.conferenceplanner.ui.presentation.home

import com.gdi.conferenceplanner.data.model.remote.responses.CourseWithSpeakersJSON

sealed class HomeEvent {
    data class OnCourseClick(val course: CourseWithSpeakersJSON): HomeEvent()
    data class OnSaveClick(val course: CourseWithSpeakersJSON): HomeEvent()
    data class OnMoreInfoClick(val course: CourseWithSpeakersJSON): HomeEvent()

}