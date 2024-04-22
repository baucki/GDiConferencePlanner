package com.gdi.conferenceplanner.data.model.remote.requests

data class ChangePasswordRequest(
    val username: String,
    val oldPassword: String,
    val newPassword: String
)
