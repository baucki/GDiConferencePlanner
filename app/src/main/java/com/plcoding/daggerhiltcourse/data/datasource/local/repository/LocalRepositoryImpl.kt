package com.plcoding.daggerhiltcourse.data.datasource.local.repository

import com.plcoding.daggerhiltcourse.data.datasource.local.CourseDao
import com.plcoding.daggerhiltcourse.data.model.Course
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImpl(
    private val courseDao: CourseDao
): LocalRepository {
    override suspend fun insertCourse(course: Course) {
        courseDao.insertCourse(course)
    }

    override suspend fun deleteCourse(course: Course) {
        courseDao.deleteCourse(course)
    }

    override suspend fun getCourseById(id: Int): Course? {
        return courseDao.getCourseById(id)
    }

    override fun getCourses(): Flow<List<Course>> {
        return courseDao.getCourses()
    }
}