package com.plcoding.daggerhiltcourse.ui.presentation.home

import com.plcoding.daggerhiltcourse.data.model.Course

sealed class HomeEvent {
    data class OnCourseClick(val course: Course): HomeEvent()
    data class OnSaveClick(val course: Course): HomeEvent()
    data class OnMoreInfoClick(val course: Course): HomeEvent()

}