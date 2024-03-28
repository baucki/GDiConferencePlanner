package com.plcoding.daggerhiltcourse.data.datasource.local.repository

import com.plcoding.daggerhiltcourse.data.model.Course
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun insertCourse(course: Course)

    suspend fun deleteCourse(course: Course)

    suspend fun getCourseById(id: Int): Course?

    fun getCourses(): Flow<List<Course>>

}