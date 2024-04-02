package com.plcoding.daggerhiltcourse.data.datasource.remote.repository

import com.plcoding.daggerhiltcourse.data.model.Course
import com.plcoding.daggerhiltcourse.data.model.CourseJSON
import com.plcoding.daggerhiltcourse.data.model.CourseWithSpeakers
import com.plcoding.daggerhiltcourse.data.model.CourseWithSpeakersJSON

interface RemoteRepository {
    suspend fun fetchAllCourses(): List<CourseWithSpeakersJSON>
    suspend fun fetchCourseById(id: Int): CourseWithSpeakersJSON?
}