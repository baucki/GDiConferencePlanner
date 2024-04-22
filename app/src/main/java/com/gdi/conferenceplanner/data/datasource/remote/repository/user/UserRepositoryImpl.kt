package com.gdi.conferenceplanner.data.datasource.remote.repository.user

import com.gdi.conferenceplanner.data.datasource.remote.MyApi
import com.gdi.conferenceplanner.data.model.remote.requests.ChangePasswordRequest
import com.gdi.conferenceplanner.data.model.remote.requests.ChangePersonalInformationRequest
import com.gdi.conferenceplanner.data.model.remote.requests.ChangeUsernameRequest
import com.gdi.conferenceplanner.data.model.remote.responses.User
import com.gdi.conferenceplanner.data.model.remote.requests.LoginRequest
import com.gdi.conferenceplanner.data.model.remote.responses.Token
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: MyApi
): UserRepository {
    override suspend fun addUser(user: User): Token {
        return api.addUser(user)
    }
    override suspend fun login(user: LoginRequest): Token {
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