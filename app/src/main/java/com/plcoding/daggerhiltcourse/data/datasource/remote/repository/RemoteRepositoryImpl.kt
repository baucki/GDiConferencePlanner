package com.plcoding.daggerhiltcourse.data.datasource.remote.repository

import com.plcoding.daggerhiltcourse.data.model.Course
import com.plcoding.daggerhiltcourse.data.datasource.remote.MyApi
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val api: MyApi,
): RemoteRepository {
    override suspend fun fetchAllCourses(): List<Course> {
        return api.fetchAllCourses()
    }
    override suspend fun fetchCourseById(id: Int): Course {
        return api.fetchCourseById(id.toString())!!
    }
}