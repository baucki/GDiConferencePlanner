package com.gdi.conferenceplanner.data.model.local.entities

import androidx.room.Entity
@Entity(primaryKeys = ["courseId", "speakerId"])
data class CourseSpeakerCrossRef(
    val courseId: Long,
    val speakerId: Long
)
