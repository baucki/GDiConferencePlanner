package com.plcoding.daggerhiltcourse.data.model.remote.requests

data class ChangePasswordRequest(
    val username: String,
    val oldPassword: String,
    val newPassword: String
)
