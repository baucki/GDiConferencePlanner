package com.gdi.conferenceplanner.data.model.remote.responses

data class CourseWithSpeakersJSON (
    val title: String,
    val description: String,
    val location: String,
    val startTime: String,
    val endTime: String,
    val id: Long,
    val speakers: List<SpeakerJSON>
)