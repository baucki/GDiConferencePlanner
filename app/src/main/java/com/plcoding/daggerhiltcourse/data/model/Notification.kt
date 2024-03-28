package com.plcoding.daggerhiltcourse.data.model

import java.time.LocalDateTime

data class Notification(
    val time: LocalDateTime,
    val message: String
)
