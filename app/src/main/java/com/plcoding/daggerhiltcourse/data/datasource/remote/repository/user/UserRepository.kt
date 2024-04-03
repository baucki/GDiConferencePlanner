package com.plcoding.daggerhiltcourse.data.datasource.remote.repository.user

import com.plcoding.daggerhiltcourse.data.model.User

interface UserRepository {
    suspend fun addUser(user: User): User?
    suspend fun login(user: User): Boolean
}