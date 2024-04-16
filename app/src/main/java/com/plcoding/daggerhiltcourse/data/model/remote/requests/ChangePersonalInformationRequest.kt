package com.plcoding.daggerhiltcourse.data.model.remote.requests

data class ChangePersonalInformationRequest(
    val imagePath: String,
    val username: String,
    val name: String,
    val lastName: String,
    val profession: String,
    val email: String,
    val phone: String,
    val country: String,
    val city: String,
)
