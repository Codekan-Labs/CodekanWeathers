package com.codekan.weathers.data

import com.codekan.weathers.data.api.WeatherApi
import com.codekan.weathers.data.api.mapper.toDomain
import com.codekan.weathers.domain.model.Forecast
import com.codekan.weathers.domain.model.Weather

class WeatherRepository(
    private val api: WeatherApi
) {
    suspend fun getWeather(city: String): Result<Weather> {
        return api.getCurrentWeather(city).map { it.toDomain() }
    }

    suspend fun getForecast(city: String,dayCount : Int): Result<Forecast> {
        return api.getForecast(city,dayCount).map { it.toDomain() }
    }
}