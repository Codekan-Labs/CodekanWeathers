package com.codekan.weathers.domain.usecase

import com.codekan.weathers.data.WeatherRepository
import com.codekan.weathers.data.api.DataState
import com.codekan.weathers.domain.model.Weather
import kotlinx.coroutines.flow.Flow

class GetWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String): Flow<DataState<Weather>> {
        return repository.getWeather(city)
    }
}
