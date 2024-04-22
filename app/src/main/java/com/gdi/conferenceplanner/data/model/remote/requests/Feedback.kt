package com.gdi.conferenceplanner.data.model.remote.requests

import com.gdi.conferenceplanner.data.model.remote.responses.CourseJSON

data class Feedback (
    val rating: String,
    val description: String,
    val course: CourseJSON
)