package com.gdi.conferenceplanner.data.datasource.remote.repository.course

import com.gdi.conferenceplanner.data.model.remote.responses.CourseWithSpeakersJSON

interface RemoteRepository {
    suspend fun fetchAllCourses(): List<CourseWithSpeakersJSON>
    suspend fun fetchCourseById(id: Int): CourseWithSpeakersJSON?
}