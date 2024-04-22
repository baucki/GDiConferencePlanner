package com.gdi.conferenceplanner.data.datasource.local.repository.course

import com.gdi.conferenceplanner.data.model.local.entities.Course
import com.gdi.conferenceplanner.data.model.local.CourseWithSpeakers
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun insertCourse(course: Course)

    suspend fun deleteCourseWithSpeakers(course: Course)

    suspend fun getCourseById(id: Int): CourseWithSpeakers?

    fun getCourses(): Flow<List<CourseWithSpeakers>>

}