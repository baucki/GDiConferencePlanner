package com.plcoding.daggerhiltcourse.data.datasource.remote.repository.user

import com.plcoding.daggerhiltcourse.data.datasource.remote.MyApi
import com.plcoding.daggerhiltcourse.data.model.remote.requests.ChangePasswordRequest
import com.plcoding.daggerhiltcourse.data.model.remote.requests.ChangePersonalInformationRequest
import com.plcoding.daggerhiltcourse.data.model.remote.requests.ChangeUsernameRequest
import com.plcoding.daggerhiltcourse.data.model.remote.responses.User
import com.plcoding.daggerhiltcourse.data.model.remote.requests.LoginRequest
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: MyApi
): UserRepository {
    override suspend fun addUser(user: User): User? {
        return api.addUser(user)
    }
    override suspend fun login(user: LoginRequest): Boolean {
        return api.login(user)
    }

    override suspend fun changeUsername(request: ChangeUsernameRequest): Boolean {
        return api.changeUsername(request)
    }

    override suspend fun changePassword(request: ChangePasswordRequest): Boolean {
        return api.changePassword(request)
    }

    override suspend fun changePersonalInformation(request: ChangePersonalInformationRequest): Boolean {
        return api.changePersonalInformation(request)
    }

    override suspend fun findUserByUsername(username: String): User? {
        return api.fetchUserByUsername(username)
    }
}