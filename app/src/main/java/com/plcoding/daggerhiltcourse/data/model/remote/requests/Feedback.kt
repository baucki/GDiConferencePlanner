package com.plcoding.daggerhiltcourse.data.model.remote.requests

import com.plcoding.daggerhiltcourse.data.model.remote.responses.CourseJSON

data class Feedback (
    val rating: String,
    val description: String,
    val course: CourseJSON
)