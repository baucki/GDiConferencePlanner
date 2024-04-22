package com.gdi.conferenceplanner.data.model.remote.requests

data class ChangeUsernameRequest(
    val oldUsername: String,
    val newUsername: String
)
