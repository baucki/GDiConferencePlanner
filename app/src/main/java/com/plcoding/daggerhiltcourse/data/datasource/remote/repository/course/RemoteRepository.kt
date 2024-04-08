package com.plcoding.daggerhiltcourse.data.datasource.remote.repository.course

import com.plcoding.daggerhiltcourse.data.model.remote.responses.CourseWithSpeakersJSON

interface RemoteRepository {
    suspend fun fetchAllCourses(): List<CourseWithSpeakersJSON>
    suspend fun fetchCourseById(id: Int): CourseWithSpeakersJSON?
}