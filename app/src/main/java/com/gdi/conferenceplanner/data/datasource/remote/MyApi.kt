package com.gdi.conferenceplanner.data.datasource.remote

import com.gdi.conferenceplanner.data.model.remote.responses.Client
import com.gdi.conferenceplanner.data.model.remote.responses.CourseWithSpeakersJSON
import com.gdi.conferenceplanner.data.model.remote.requests.Feedback
import com.gdi.conferenceplanner.data.model.remote.responses.SpeakerJSON
import com.gdi.conferenceplanner.data.model.remote.responses.User
import com.gdi.conferenceplanner.data.model.remote.requests.ChangePasswordRequest
import com.gdi.conferenceplanner.data.model.remote.requests.ChangePersonalInformationRequest
import com.gdi.conferenceplanner.data.model.remote.requests.ChangeUsernameRequest
import com.gdi.conferenceplanner.data.model.remote.requests.LoginRequest
import com.gdi.conferenceplanner.data.model.remote.responses.Token
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MyApi {
    @POST("users/login")
    suspend fun login(@Body user: LoginRequest): Token
    @POST("users/add")
    suspend fun addUser(@Body user: User): Token
    @POST("users/change-username")
    suspend fun changeUsername(@Body request: ChangeUsernameRequest): Boolean
    @POST("users/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): Boolean

    @POST("users/change-personal-information")
    suspend fun changePersonalInformation(@Body request: ChangePersonalInformationRequest): Boolean
    @GET("/users/find/{username}")
    suspend fun fetchUserByUsername(@Path("username") username: String): User?
    @GET("clients/all")
    suspend fun fetchAllClients(): List<Client>
    @POST("feedbacks/add")
    suspend fun addFeedback(@Body feedback: Feedback): Feedback?
    @GET("courses/all")
    suspend fun fetchAllCourses(): List<CourseWithSpeakersJSON>
    @GET("courses/find/{id}")
    suspend fun fetchCourseById(@Path("id") id: String): CourseWithSpeakersJSON?
    @GET("speakers/find/{id}")
    suspend fun fetchSpeakersById(@Path("id") id: String): SpeakerJSON?

}