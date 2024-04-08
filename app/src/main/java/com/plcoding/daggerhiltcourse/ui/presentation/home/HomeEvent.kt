package com.plcoding.daggerhiltcourse.ui.presentation.home

import com.plcoding.daggerhiltcourse.data.model.remote.responses.CourseWithSpeakersJSON

sealed class HomeEvent {
    data class OnCourseClick(val course: CourseWithSpeakersJSON): HomeEvent()
    data class OnSaveClick(val course: CourseWithSpeakersJSON): HomeEvent()
    data class OnMoreInfoClick(val course: CourseWithSpeakersJSON): HomeEvent()

}