package com.gdi.conferenceplanner.data.datasource.remote.repository.user

import com.gdi.conferenceplanner.data.model.remote.requests.ChangePasswordRequest
import com.gdi.conferenceplanner.data.model.remote.requests.ChangePersonalInformationRequest
import com.gdi.conferenceplanner.data.model.remote.requests.ChangeUsernameRequest
import com.gdi.conferenceplanner.data.model.remote.responses.User
import com.gdi.conferenceplanner.data.model.remote.requests.LoginRequest
import com.gdi.conferenceplanner.data.model.remote.responses.Token

interface UserRepository {
    suspend fun addUser(user: User): Token
    suspend fun login(user: LoginRequest): Token
    suspend fun changeUsername(request: ChangeUsernameRequest): Boolean
    suspend fun changePassword(request: ChangePasswordRequest): Boolean
    suspend fun changePersonalInformation(request: ChangePersonalInformationRequest): Boolean
    suspend fun findUserByUsername(username: String): User?
}