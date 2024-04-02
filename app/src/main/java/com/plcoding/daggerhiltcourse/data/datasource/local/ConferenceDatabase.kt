package com.plcoding.daggerhiltcourse.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.daggerhiltcourse.data.datasource.local.dao.CourseDao
import com.plcoding.daggerhiltcourse.data.datasource.local.dao.CourseSpeakerCrossRefDao
import com.plcoding.daggerhiltcourse.data.datasource.local.dao.SpeakerDao
import com.plcoding.daggerhiltcourse.data.model.Course
import com.plcoding.daggerhiltcourse.data.model.CourseSpeakerCrossRef
import com.plcoding.daggerhiltcourse.data.model.Speaker

@Database(
    entities = [Course::class, Speaker::class, CourseSpeakerCrossRef::class],
    version = 1
)
abstract class ConferenceDatabase: RoomDatabase() {
    abstract val courseDao: CourseDao
    abstract val speakerDao: SpeakerDao
    abstract val crossRefDao: CourseSpeakerCrossRefDao
}