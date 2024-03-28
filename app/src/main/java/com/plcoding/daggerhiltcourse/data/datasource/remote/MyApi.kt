package com.plcoding.daggerhiltcourse.data.datasource.remote

import com.plcoding.daggerhiltcourse.data.model.Course
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Path

interface MyApi {

//    @GET("all")
//    suspend fun doNetworkCall(): ResponseBody
    @GET("all")
    suspend fun fetchAllCourses(): List<Course>

    @GET("find/{id}")
    suspend fun fetchCourseById(@Path("id") id: String): Course?

}