package com.gdi.conferenceplanner.data.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

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