package com.codekan.weathers.data.util

import platform.Foundation.NSDate
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitHour

actual object TimeUtils {
    actual fun isDayTime(): Boolean {
        val calendar = NSCalendar.currentCalendar
        val components = calendar.components(
            NSCalendarUnitHour,
            NSDate()
        )
        val currentHour = components.hour.toInt()
        return currentHour in 6..17 // 06:00 - 17:59 arası gündüz
    }
}

