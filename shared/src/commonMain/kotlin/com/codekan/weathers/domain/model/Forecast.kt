package com.codekan.weathers.domain.model

data class Forecast(
    val city: String,
    val country: String,
    val forecastList: List<ForecastItem>
)

data class ForecastItem(
    val date: String,
    val temperature: Double,
    val humidity: Int,
    val windSpeed: Double,
    val description: String
)