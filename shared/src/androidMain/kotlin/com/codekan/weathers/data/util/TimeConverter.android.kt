package com.codekan.weathers.data.util


import android.os.Build
import java.time.LocalTime
import java.util.Calendar

actual object TimeUtils {
    actual fun isDayTime(): Boolean {
        val currentHour = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalTime.now().hour
        } else {
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        }
        return currentHour in 6..17 // 06:00 - 17:59 arası gündüz
    }
}