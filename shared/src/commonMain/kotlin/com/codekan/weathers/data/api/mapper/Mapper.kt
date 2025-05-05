package com.codekan.weathers.data.api.mapper

import com.codekan.weathers.data.api.model.WeatherResponse
import com.codekan.weathers.data.util.CountryMapper
import com.codekan.weathers.data.util.TimeConverter
import com.codekan.weathers.data.util.humidityToString
import com.codekan.weathers.data.util.rainToString
import com.codekan.weathers.data.util.temperatureToString
import com.codekan.weathers.data.util.windSpeedToString
import io.ktor.util.toUpperCasePreservingASCIIRules

fun WeatherResponse.toDomain(): com.codekan.weathers.domain.model.Weather {
    return com.codekan.weathers.domain.model.Weather(
        city = this.name ?: "",
        country = CountryMapper.getCountryName(this.sys?.country) ?: "",
        temperature = temperatureToString(this.main?.temp),
        humidity = humidityToString(this.main?.humidity),
        windSpeed = windSpeedToString(this.wind?.speed),
        conditionDescription = this.weather[0].description?.toUpperCasePreservingASCIIRules() ?: "",
        feelsLike = temperatureToString(this.main?.feelsLike),
        rainMmHour = rainToString(this.rain?.oneh),
        sunriseTime =  TimeConverter.formatTimestampToHourMinute(this.sys?.sunrise) ?: "--:--",
        sunsetTime = TimeConverter.formatTimestampToHourMinute(this.sys?.sunset) ?: "--:--",
        iconCode = this.weather[0].icon ?: "",
        timeZone = this.timezone ?: 0
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