package com.plcoding.daggerhiltcourse.data.model.remote.responses

data class User(
    val username: String,
    val password: String,
    val name: String,
    val lastName: String,
    val imagePath: String,
    val email: String,
    val country: String,
    val city: String,
    val profession: String,
    val phone: String
)
