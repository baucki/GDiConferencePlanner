package com.plcoding.daggerhiltcourse.data.datasource.remote

import com.plcoding.daggerhiltcourse.data.model.Client
import com.plcoding.daggerhiltcourse.data.model.Course
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Path

interface MyApi {
    @GET("course/all")
    suspend fun fetchAllCourses(): List<Course>

    @GET("clients/all")
    suspend fun fetchAllClients(): List<Client>

    @GET("course/find/{id}")
    suspend fun fetchCourseById(@Path("id") id: String): Course?

}