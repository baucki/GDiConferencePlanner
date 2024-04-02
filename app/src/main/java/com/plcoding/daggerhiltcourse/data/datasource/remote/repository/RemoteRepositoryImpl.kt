package com.plcoding.daggerhiltcourse.data.datasource.remote.repository

import com.plcoding.daggerhiltcourse.data.model.Course
import com.plcoding.daggerhiltcourse.data.datasource.remote.MyApi
import com.plcoding.daggerhiltcourse.data.model.CourseJSON
import com.plcoding.daggerhiltcourse.data.model.CourseWithSpeakers
import com.plcoding.daggerhiltcourse.data.model.CourseWithSpeakersJSON
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val api: MyApi,
): RemoteRepository {
    override suspend fun fetchAllCourses(): List<CourseWithSpeakersJSON> {
        return api.fetchAllCourses()
    }
    override suspend fun fetchCourseById(id: Int): CourseWithSpeakersJSON {
        return api.fetchCourseById(id.toString())!!
    }
}