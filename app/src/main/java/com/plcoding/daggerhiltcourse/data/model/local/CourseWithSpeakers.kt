package com.plcoding.daggerhiltcourse.data.model.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.plcoding.daggerhiltcourse.data.model.local.entities.Course
import com.plcoding.daggerhiltcourse.data.model.local.entities.CourseSpeakerCrossRef
import com.plcoding.daggerhiltcourse.data.model.local.entities.Speaker

data class CourseWithSpeakers(
    @Embedded val course: Course,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "speakerId",
        associateBy = Junction(CourseSpeakerCrossRef::class)
    )
    val speakers: List<Speaker>
)