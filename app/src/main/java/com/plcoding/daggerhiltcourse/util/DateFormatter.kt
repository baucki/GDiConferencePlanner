package com.plcoding.daggerhiltcourse.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class DateFormatter {
    companion object {
        fun formatDate(dateString: String): String {
            val date = LocalDate.parse(dateString)

            val formatter = DateTimeFormatter.ofPattern("MMM d", Locale.ENGLISH)
            return date.format(formatter)
        }
        fun dateToLocalTime(dateString: String): LocalDateTime {
            val pattern = "yyyy-MM-dd'T'HH:mm:ss"
            return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(pattern))
        }
    }
}