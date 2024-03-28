package com.plcoding.daggerhiltcourse.ui.presentation.my_agenda

import com.plcoding.daggerhiltcourse.data.model.Course

sealed class MyAgendaEvent {
    data class OnCourseClick(val course: Course): MyAgendaEvent()

}