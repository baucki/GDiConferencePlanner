package com.plcoding.daggerhiltcourse.data.datasource.local.repository.course

import com.plcoding.daggerhiltcourse.data.model.Course
import com.plcoding.daggerhiltcourse.data.model.CourseWithSpeakers
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun insertCourse(course: Course)

    suspend fun deleteCourseWithSpeakers(course: Course)

    suspend fun getCourseById(id: Int): CourseWithSpeakers?

    fun getCourses(): Flow<List<CourseWithSpeakers>>

}