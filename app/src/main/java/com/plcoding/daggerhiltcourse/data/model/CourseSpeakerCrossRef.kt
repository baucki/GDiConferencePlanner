package com.plcoding.daggerhiltcourse.data.model

import androidx.room.Entity
@Entity(primaryKeys = ["courseId", "speakerId"])
data class CourseSpeakerCrossRef(
    val courseId: Long,
    val speakerId: Long
)
