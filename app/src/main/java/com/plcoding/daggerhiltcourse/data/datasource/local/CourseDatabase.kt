package com.plcoding.daggerhiltcourse.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.daggerhiltcourse.data.model.Course

@Database(
    entities = [Course::class],
    version = 1
)
abstract class CourseDatabase: RoomDatabase() {
    abstract val courseDao: CourseDao
}