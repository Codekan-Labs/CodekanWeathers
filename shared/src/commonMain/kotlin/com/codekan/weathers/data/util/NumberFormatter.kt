package com.codekan.weathers.data.util

import kotlin.math.floor

object NumberFormatter {
    fun roundOneDecimal(number: Double?): String {
        if (number == null) {
            return "N/A"
        }
        // Sayıyı 10’la çarp, yuvarla, 10’a böl
        val scaled = number * 10
        val rounded = if (scaled >= 0) {
            floor(scaled + 0.5) // Yukarı yuvarlama için 0.5 ekle
        } else {
            floor(scaled - 0.5) // Negatif sayılar için
        } / 10.0
        // String’e çevir, 1 ondalık basamak garantile
        val formatted = rounded.toString()
        return when {
            formatted.endsWith(".0") -> formatted.dropLast(2) // 12.0 → "12"
            !formatted.contains(".") -> "$formatted.0" // 12 → "12.0"
            else -> {
                val decimalPart = formatted.substringAfter(".")
                if (decimalPart.length > 1) {
                    formatted.substring(0, formatted.indexOf(".") + 2) // 12.46 → "12.5"
                } else {
                    formatted // Zaten 12.5 gibi
                }
            }
        }
    }
}