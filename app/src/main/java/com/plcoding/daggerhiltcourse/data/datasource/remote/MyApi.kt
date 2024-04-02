package com.plcoding.daggerhiltcourse.data.datasource.remote

import com.plcoding.daggerhiltcourse.data.model.Client
import com.plcoding.daggerhiltcourse.data.model.Course
import com.plcoding.daggerhiltcourse.data.model.CourseJSON
import com.plcoding.daggerhiltcourse.data.model.CourseWithSpeakers
import com.plcoding.daggerhiltcourse.data.model.CourseWithSpeakersJSON
import com.plcoding.daggerhiltcourse.data.model.Feedback
import com.plcoding.daggerhiltcourse.data.model.SpeakerJSON
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MyApi {
    @GET("courses/all")
    suspend fun fetchAllCourses(): List<CourseWithSpeakersJSON>
    @GET("clients/all")
    suspend fun fetchAllClients(): List<Client>
    @POST("feedbacks/add")
    suspend fun addFeedback(@Body feedback: Feedback): Feedback?
    @GET("courses/find/{id}")
    suspend fun fetchCourseById(@Path("id") id: String): CourseWithSpeakersJSON?

    @GET("speakers/find/{id}")
    suspend fun fetchSpeakersById(@Path("id") id: String): SpeakerJSON?

}