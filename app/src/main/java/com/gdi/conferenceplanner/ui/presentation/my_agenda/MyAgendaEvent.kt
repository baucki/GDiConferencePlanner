package com.gdi.conferenceplanner.ui.presentation.my_agenda

import com.gdi.conferenceplanner.data.model.local.entities.Course

sealed class MyAgendaEvent {
    data class OnCourseClick(val course: Course): MyAgendaEvent()

}