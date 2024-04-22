package com.gdi.conferenceplanner.data.datasource.remote.repository.course

import com.gdi.conferenceplanner.data.datasource.remote.MyApi
import com.gdi.conferenceplanner.data.model.remote.responses.CourseWithSpeakersJSON
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