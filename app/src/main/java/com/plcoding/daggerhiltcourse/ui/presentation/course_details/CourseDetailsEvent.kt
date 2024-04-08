package com.plcoding.daggerhiltcourse.ui.presentation.course_details

import com.plcoding.daggerhiltcourse.data.model.remote.responses.CourseJSON
import com.plcoding.daggerhiltcourse.data.model.remote.responses.SpeakerJSON
import com.plcoding.daggerhiltcourse.util.AlarmScheduler

sealed class CourseDetailsEvent {
    data class OnSaveClick(val courseJSON: CourseJSON, val speakersJSON: List<SpeakerJSON>, val scheduler: AlarmScheduler): CourseDetailsEvent()
    data class OnSpeakerClick(val speakerId: Long): CourseDetailsEvent()
}