package com.plcoding.daggerhiltcourse.data.model

import androidx.room.PrimaryKey

data class CourseJSON (
    val title: String,
    val description: String,
    val location: String,
    val startTime: String,
    val endTime: String,
    val id: Long
)