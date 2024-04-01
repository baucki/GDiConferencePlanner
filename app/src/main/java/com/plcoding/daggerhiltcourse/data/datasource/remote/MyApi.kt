package com.plcoding.daggerhiltcourse.data.datasource.remote

import com.plcoding.daggerhiltcourse.data.model.Client
import com.plcoding.daggerhiltcourse.data.model.Course
import com.plcoding.daggerhiltcourse.data.model.Feedback
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MyApi {
    @GET("courses/all")
    suspend fun fetchAllCourses(): List<Course>

    @GET("clients/all")
    suspend fun fetchAllClients(): List<Client>

    @POST("feedbacks/add")
    suspend fun addFeedback(@Body feedback: Feedback): Feedback?

    @GET("courses/find/{id}")
    suspend fun fetchCourseById(@Path("id") id: String): Course?

}