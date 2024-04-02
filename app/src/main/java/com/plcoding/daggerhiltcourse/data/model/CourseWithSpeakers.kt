package com.plcoding.daggerhiltcourse.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CourseWithSpeakers(
    @Embedded val course: Course,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "speakerId",
        associateBy = Junction(CourseSpeakerCrossRef::class)
    )
    val speakers: List<Speaker>
)