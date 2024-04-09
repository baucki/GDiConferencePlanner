package com.plcoding.daggerhiltcourse.data.datasource.remote.repository.user

import com.plcoding.daggerhiltcourse.data.model.remote.requests.ChangePasswordRequest
import com.plcoding.daggerhiltcourse.data.model.remote.requests.ChangePersonalInformationRequest
import com.plcoding.daggerhiltcourse.data.model.remote.requests.ChangeUsernameRequest
import com.plcoding.daggerhiltcourse.data.model.remote.responses.User
import com.plcoding.daggerhiltcourse.data.model.remote.requests.LoginRequest

interface UserRepository {
    suspend fun addUser(user: User): Boolean
    suspend fun login(user: LoginRequest): Boolean
    suspend fun changeUsername(request: ChangeUsernameRequest): Boolean
    suspend fun changePassword(request: ChangePasswordRequest): Boolean
    suspend fun changePersonalInformation(request: ChangePersonalInformationRequest): Boolean
    suspend fun findUserByUsername(username: String): User?
}