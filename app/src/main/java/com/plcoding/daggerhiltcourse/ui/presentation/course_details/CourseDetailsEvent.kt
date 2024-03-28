package com.plcoding.daggerhiltcourse.ui.presentation.course_details

import com.plcoding.daggerhiltcourse.data.model.Course

sealed class CourseDetailsEvent {
    data class OnSaveClick(val course: Course): CourseDetailsEvent()
    object OnBackClick: CourseDetailsEvent()
}