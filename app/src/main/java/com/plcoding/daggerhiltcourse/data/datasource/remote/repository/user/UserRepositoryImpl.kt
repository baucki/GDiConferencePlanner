package com.plcoding.daggerhiltcourse.data.datasource.remote.repository.user

import com.plcoding.daggerhiltcourse.data.datasource.remote.MyApi
import com.plcoding.daggerhiltcourse.data.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: MyApi
): UserRepository {
    override suspend fun addUser(user: User): User? {
        return api.addUser(user)
    }
    override suspend fun login(user: User): Boolean {
        return api.login(user)
    }
}