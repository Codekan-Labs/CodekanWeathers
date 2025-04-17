package com.codekan.weathers.domain.usecase

import com.codekan.weathers.data.WeatherRepository
import com.codekan.weathers.domain.model.Weather

class GetWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String): Result<Weather> {
        return repository.getWeather(city)
    }
}