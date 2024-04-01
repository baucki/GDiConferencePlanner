package com.plcoding.daggerhiltcourse.data.model


data class Speaker(
    val imageUrl: String,
    val name: String,
    val title: String,
    val biography: String,
    val course: Course
)
