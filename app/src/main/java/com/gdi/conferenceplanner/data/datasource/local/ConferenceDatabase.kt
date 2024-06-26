package com.gdi.conferenceplanner.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gdi.conferenceplanner.data.datasource.local.dao.CourseDao
import com.gdi.conferenceplanner.data.datasource.local.dao.CourseSpeakerCrossRefDao
import com.gdi.conferenceplanner.data.datasource.local.dao.SpeakerDao
import com.gdi.conferenceplanner.data.model.local.entities.Course
import com.gdi.conferenceplanner.data.model.local.entities.CourseSpeakerCrossRef
import com.gdi.conferenceplanner.data.model.local.entities.Speaker

@Database(
    entities = [Course::class, Speaker::class, CourseSpeakerCrossRef::class],
    version = 1
)
abstract class ConferenceDatabase: RoomDatabase() {
    abstract val courseDao: CourseDao
    abstract val speakerDao: SpeakerDao
    abstract val crossRefDao: CourseSpeakerCrossRefDao
}