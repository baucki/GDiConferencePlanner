package com.plcoding.daggerhiltcourse.data.datasource.remote.repository

import com.plcoding.daggerhiltcourse.data.model.Course

interface RemoteRepository {
    suspend fun fetchAllCourses(): List<Course>

    suspend fun fetchCourseById(id: Int): Course?
}