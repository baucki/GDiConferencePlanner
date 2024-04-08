package com.plcoding.daggerhiltcourse.data.datasource.local.repository.course

import com.plcoding.daggerhiltcourse.data.datasource.local.dao.CourseDao
import com.plcoding.daggerhiltcourse.data.model.local.entities.Course
import com.plcoding.daggerhiltcourse.data.model.local.CourseWithSpeakers
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImpl(
    private val courseDao: CourseDao
): LocalRepository {
    override suspend fun insertCourse(course: Course) {
        courseDao.insertCourse(course)
    }

    override suspend fun deleteCourseWithSpeakers(course: Course) {
        courseDao.deleteCourseWithSpeakers(course)
    }

    override suspend fun getCourseById(id: Int): CourseWithSpeakers? {
        return courseDao.getCourseById(id)
    }

    override fun getCourses(): Flow<List<CourseWithSpeakers>> {
        return courseDao.getCourses()
    }
}