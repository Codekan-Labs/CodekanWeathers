package com.codekan.weathers.data.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object TimeConverter {
    fun formatTimestampToHourMinute(timestamp: Long?, timeZone: TimeZone = TimeZone.currentSystemDefault()): String? {
        if (timestamp == null) {
            return null
        }
        val instant = Instant.fromEpochSeconds(timestamp)
        val localDateTime = instant.toLocalDateTime(timeZone)
        val hour = localDateTime.hour.toString().padStart(2, '0') // %02d yerine
        val minute = localDateTime.minute.toString().padStart(2, '0') // %02d yerine
        return "$hour:$minute"
    }
}

expect object TimeUtils {
    fun isDayTime(): Boolean
}