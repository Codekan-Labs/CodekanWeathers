package com.codekan.weathers.domain.usecase

import com.codekan.weathers.data.WeatherRepository
import com.codekan.weathers.domain.model.Forecast

class GetForecastUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String, dayCount : Int): Result<Forecast> {
        return repository.getForecast(city, dayCount)
    }
}