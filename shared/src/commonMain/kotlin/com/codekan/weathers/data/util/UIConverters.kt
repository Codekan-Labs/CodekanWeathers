package com.codekan.weathers.data.util

fun humidityToString(humidity: Int?): String {
    if (humidity == null) {
        return "N/A"
    }
    return "$humidity%"
}

fun windSpeedToString(windSpeed: Double?): String {
    if (windSpeed == null) {
        return "N/A"
    }
    return "${NumberFormatter.roundOneDecimal(windSpeed)} m/s"
}

fun temperatureToString(temperature: Double?): String {
    if (temperature == null) {
        return "N/A"
    }
    return "${NumberFormatter.roundOneDecimal(temperature)}°C"
}

fun rainToString(rain: Double?): String {
    if (rain == null) {
        return "N/A"
    }
    return "${NumberFormatter.roundOneDecimal(rain)} mm/h"
}

private const val BASE_URL = "https://openweathermap.org/img/wn/"
private const val SUFFIX = "@2x.png"
fun getWeatherIconUrl(iconCode : String): String {
    return iconCode.let { "$BASE_URL$it$SUFFIX" }
}