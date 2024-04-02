package com.plcoding.daggerhiltcourse.data.model

data class Feedback (
    val rating: String,
    val description: String,
    val course: CourseJSON
)