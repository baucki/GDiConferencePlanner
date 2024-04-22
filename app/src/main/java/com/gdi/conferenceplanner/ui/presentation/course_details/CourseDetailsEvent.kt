package com.gdi.conferenceplanner.ui.presentation.course_details

import com.gdi.conferenceplanner.data.model.remote.responses.CourseJSON
import com.gdi.conferenceplanner.data.model.remote.responses.SpeakerJSON
import com.gdi.conferenceplanner.util.AlarmScheduler

sealed class CourseDetailsEvent {
    data class OnSaveClick(val courseJSON: CourseJSON, val speakersJSON: List<SpeakerJSON>, val scheduler: AlarmScheduler): CourseDetailsEvent()
    data class OnSpeakerClick(val speakerId: Long): CourseDetailsEvent()
}