package com.gdi.conferenceplanner.data.model

import java.time.LocalDateTime

data class Notification(
    val time: LocalDateTime,
    val message: String
)
