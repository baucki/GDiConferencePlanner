package com.plcoding.daggerhiltcourse.data.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "courses")
data class Course(
    val title: String,
    val description: String,
    val location: String,
    val startTime: String,
    val endTime: String,
    @PrimaryKey
    val courseId: Long
)