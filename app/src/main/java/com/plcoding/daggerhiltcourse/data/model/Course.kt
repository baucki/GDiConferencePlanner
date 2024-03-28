package com.plcoding.daggerhiltcourse.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Course(
    val title: String,
    val description: String,
    val location: String,
    val startTime: String,
    val endTime: String,
    val instructor: String,
    val imageUrl: String,
    @PrimaryKey
    val id: Int? = null,
)