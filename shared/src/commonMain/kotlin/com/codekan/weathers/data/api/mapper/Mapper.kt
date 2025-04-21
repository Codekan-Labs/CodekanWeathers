package com.codekan.weathers.data.api.mapper

import com.codekan.weathers.data.api.model.WeatherResponse

fun WeatherResponse.toDomain(): com.codekan.weathers.domain.model.Weather {
    return com.codekan.weathers.domain.model.Weather(
        city = this.name ?: "",
        country = "",
        temperature = this.main?.temp ?: 0.0,
        humidity = this.main?.humidity ?: 0,
        windSpeed = this.wind?.speed ?: 0.0,
        description = this.name ?: "",
    )
}

fun com.codekan.weathers.data.api.model.ForecastResponse.toDomain(): com.codekan.weathers.domain.model.Forecast {
    return com.codekan.weathers.domain.model.Forecast(
        city = this.city?.name ?: "",
        country = this.city?.country ?: "",
        forecastList = this.list.map { forecast ->
            com.codekan.weathers.domain.model.ForecastItem(
                date = forecast.dt.toString(),
                temperature = forecast.main?.temp ?: 0.0,
                humidity = forecast.main?.humidity ?: 0,
                windSpeed = forecast.wind?.speed ?: 0.0,
                description = forecast.weather.firstOrNull()?.description ?: ""
            )
        }
    )
}